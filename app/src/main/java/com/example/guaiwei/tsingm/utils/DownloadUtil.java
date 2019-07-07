package com.example.guaiwei.tsingm.utils;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.util.Log;

import com.example.guaiwei.tsingm.activity.ExerciseListActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import static android.os.Build.VERSION.SDK_INT;

public class DownloadUtil {
    /**
     * 文件缓存策略
     *
     * @param url        文件路径
     * @param fileName   文件名称
     * @param fileFormat 文件格式
     */
    public static void downFile(final List<String> url, final List<String> fileName, final String fileFormat) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = "";
                    path=MyApplication.getContext().getExternalCacheDir()+"/";
                    Log.v("1","获取内部缓存");
                    File fileDir = new File(path + "test");
                    if (!fileDir.exists()) {
                        fileDir.mkdir();
                    }
                    for (int i=0;i<url.size();i++){
                        File file = new File(fileDir.getAbsolutePath() + "/" + fileName.get(i) + "." + fileFormat);
                        if (file.exists()) {
                            continue;
                        } else {
                            URL myURL = new URL(url.get(i));
                            URLConnection conn = myURL.openConnection();
                            conn.connect();
                            InputStream is = conn.getInputStream();
                            int fileSize = conn.getContentLength();
                            if (fileSize <= 0)
                                throw new RuntimeException("can not know the file`s size");
                            if (is == null)
                                throw new RuntimeException("stream is null");
                            FileOutputStream fos = new FileOutputStream(file.getPath());
                            byte buf[] = new byte[1024];
                            while (true) {
                                // 循环读取
                                int numread = is.read(buf);
                                if (numread == -1) {
                                    break;
                                }
                                fos.write(buf, 0, numread);
                            }
                            try {
                                is.close();
                            } catch (Exception ex) {
                            }
                        }
                    }
                    Message message = new Message();
                    message.what=0x0001;
                    ExerciseListActivity.handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                    Message message = new Message();
                    message.what=0x0002;
                    ExerciseListActivity.handler.sendMessage(message);
                }
            }
        }).start();
    }

    /**
     * 获取文件地址
     *
     * @param fileName   文件名称
     * @param fileFormat 文件格式
     * @return
     */
    public static String getDownFilePath(String fileName, String fileFormat) {
        String path = "";
        path = MyApplication.getContext().getExternalCacheDir() + "/";

        // 读取文件
        File file = new File(path + "test" + File.separator
                + fileName + "." + fileFormat);
        if (file.exists()) {
            return file.getAbsolutePath();
        } else {
            return "";
        }
    }
    public static void updateMedia( String path){

        if(SDK_INT >= Build.VERSION_CODES.KITKAT){//当大于等于Android 4.4时
            MediaScannerConnection.scanFile(MyApplication.getContext(), new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String path, Uri uri) {
                }
            });

        }else{//Andrtoid4.4以下版本
            MyApplication.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,Uri.fromFile((new File(path).getParentFile()))));
        }

    }

}
