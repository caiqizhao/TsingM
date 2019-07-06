package com.example.guaiwei.tsingm.activity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.example.guaiwei.tsingm.R;
import com.example.guaiwei.tsingm.utils.GetBeforeData;
import com.example.guaiwei.tsingm.gson.LatLngPoint;
import com.example.guaiwei.tsingm.db.LocationPoint;
import com.example.guaiwei.tsingm.db.MotionRecordsEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 跑步界面
 */
public class RunActivity extends AppCompatActivity implements SensorEventListener,AMapLocationListener {

    private SensorManager mSM;
    private Sensor mSensor;

    private MyLocationStyle myLocationStyle = null;

    private MapView mMapView = null;

    //声明定位回调监听器
    private AMapLocationListener mLocationListener = null;

    //初始化地图控制器对象
    private AMap aMap = null;

    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient;

    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption = null;

    //记录上次的位置信息
    private AMapLocation privLocation = null;

    //是否开启轨迹记录
    public static boolean isOpenTrajectory = true;

    //运动距离
    public static double distance = 0.0;

    //轨迹点
    private List<LatLngPoint> latLngPoints = new ArrayList<>();

    //存储获取的数据库的轨迹点
    private  List<LocationPoint> locationPoints = new ArrayList<>();

    public int time=0;//单个训练视频的计时，和总计时
    private Thread timeThread;//计时线程
    public Runnable timeRun;
    public static Handler mHandler;

    private TextView inastanceTv,timeTv;
    private Button startBtn,pauseBtn,endBtn;
    private LinearLayout startLL,pauseLL;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        initComponent();
        initPositioning();
        mHandler=new MessageUtil();
        //计时
        timeRun=new Runnable() {
            @Override
            public void run() {
                try {
                    while(!Thread.interrupted()) {
                        Message msg = new Message();//生成消息
                        msg.what = 0x001;//设置消息类型
                        Bundle data = new Bundle();//生成Bundle携带消息
                        data.putInt("time", time);
                        msg.setData(data);
                        mHandler.sendMessage(msg);
                        time++;
                        Thread.sleep(1000);
                    }
                }catch (Exception e){
                    Log.v("timeThread","线程终止了");
                }
            }
        };
        timeThread=new Thread(timeRun);
        timeThread.start();
        if(null != mLocationClient){
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
        initMap(savedInstanceState);
        setListensers();
    }

    /**
     * 为各控件设置事件监听
     */
    private void setListensers() {
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLL.setVisibility(View.GONE);
                pauseLL.setVisibility(View.VISIBLE);
                isOpenTrajectory=false;
                if (timeThread!=null&&timeThread.isAlive()){
                    timeThread.interrupt();
                    timeThread=null;
                }
            }
        });
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseLL.setVisibility(View.GONE);
                startLL.setVisibility(View.VISIBLE);
                isOpenTrajectory=true;
                if (timeThread==null){
                    timeThread=new Thread(timeRun);
                    timeThread.start();
                }
            }
        });
        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpenTrajectory=true;
                MotionRecordsEntity motionRecordsEntity=new MotionRecordsEntity();
                motionRecordsEntity.setData(GetBeforeData.getBeforeData(null,0).get(0));
                motionRecordsEntity.setMovement_type("跑步");
                motionRecordsEntity.setMovement_content("跑步"+distance+"公里");
                motionRecordsEntity.save();
//                for(LocationPoint locationPoint:locationPoints){
////                    locationPoint.save();
////                }
                locationPoints.clear();
                finish();
            }
        });
    }

    /**
     * 初始化各组件
     */
    private void initComponent() {
        inastanceTv=findViewById(R.id.instance);
        timeTv=findViewById(R.id.run_time);
        startBtn=findViewById(R.id.start_btn);
        pauseBtn=findViewById(R.id.pause_btn);
        endBtn=findViewById(R.id.end_btn);
        startLL=findViewById(R.id.start_ll);
        pauseLL=findViewById(R.id.pause_ll);
    }


    /**
     * 设置各组件数据
     */
    private void setData() {
        timeTv.setText(GetBeforeData.formatTime(time));
        inastanceTv.setText(distance+"公里");
    }


    /**
     * 定位初始化
     */
    private void initPositioning(){
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());


        //设置定位回调监听
        mLocationClient.setLocationListener(this);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(false);

        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(3000);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);

        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
    }

    /**
     * 初始化地图控件
     * @param savedInstanceState
     */
    private void initMap(Bundle savedInstanceState){
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }

        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER) ;//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        mSM = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSM.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mSM.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);//注册回调函数

//        AMapLocation aMapLocation = mLocationClient.getLastKnownLocation();
//        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 18));


    }

    /**
     * 陀螺传感器回调函数
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            float degree = event.values[0];
            float bearing = aMap.getCameraPosition().bearing;
            if (degree + bearing > 360)
                aMap.setMyLocationRotateAngle(degree + bearing - 360);// 设置小蓝点旋转角度
            else
                aMap.setMyLocationRotateAngle(degree + bearing);//

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    /**
     * 位置回调监听函数
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 18));
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                Date date = new Date(aMapLocation.getTime());
                String time = df.format(date);
                System.out.println(time);//定位时间

                //可在其中解析amapLocation获取相应内容。
                System.out.println(aMapLocation.getLocationType());//获取当前定位结果来源，如网络定位结果，详见定位类型表
                System.out.println(aMapLocation.getLatitude());//获取纬度
                System.out.println(aMapLocation.getLongitude());//获取经度
                System.out.println(aMapLocation.getAccuracy());//获取精度信息
                System.out.println(aMapLocation.getAddress());//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                System.out.println(aMapLocation.getCountry());//国家信息
                System.out.println(aMapLocation.getProvince());//省信息
                System.out.println(aMapLocation.getCity());//城市信息
                System.out.println(aMapLocation.getDistrict());//城区信息
                System.out.println(aMapLocation.getStreet());//街道信息

                if (isOpenTrajectory) {
                    //一边定位一边连线
                    drawLines(aMapLocation);
                    distance += Distance(aMapLocation);
                    privLocation = aMapLocation;
                    LocationPoint locationPoint = new LocationPoint();
                    locationPoint.setLatitude(aMapLocation.getLatitude());
                    locationPoint.setLongitude(aMapLocation.getLongitude());
                    locationPoint.setData(Integer.parseInt(time));
                    locationPoints.add(locationPoint);
                    if (aMapLocation.getLatitude() != 0 && aMapLocation.getLongitude() != 0) {
                        latLngPoints.add(new LatLngPoint( new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                    }
                }

            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }

    }

    /**
     * 绘制运动路线
     *
     * @param curLocation
     */
    public void drawLines(AMapLocation curLocation) {

        if (null == privLocation) {
            return;
        }
        if (curLocation.getLatitude() != 0.0 && curLocation.getLongitude() != 0.0
                && privLocation.getLongitude() != 0.0 && privLocation.getLatitude() != 0.0) {
            PolylineOptions options = new PolylineOptions();
            //上一个点的经纬度
            options.add(new LatLng(privLocation.getLatitude(), privLocation.getLongitude()));
            //当前的经纬度
            options.add(new LatLng(curLocation.getLatitude(), curLocation.getLongitude()));
            options.width(10).geodesic(true).color(Color.GREEN);
            aMap.addPolyline(options);
        }

    }


    /**
     * 距离计算
     * @param curLocation
     * @return
     */
    private double Distance(AMapLocation curLocation) {
        double distance;
        distance = AMapUtils.calculateLineDistance(new LatLng(privLocation.getLatitude(),
                privLocation.getLongitude()), new LatLng(curLocation.getLatitude(),
                curLocation.getLongitude()));
        distance += distance;

        return distance;
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        isOpenTrajectory=true;
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        if(null != mLocationClient){
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 消息处理内部类
     */
    private class MessageUtil extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0x001){
                setData();
            }
        }
    }
}
