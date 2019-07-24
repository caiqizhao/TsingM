package com.example.guaiwei.tsingm.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.guaiwei.tsingm.R;

import java.text.DecimalFormat;

/**
 * Created by hqq on 2019/7/13.
 */
public class EnergyProgressView extends View {

    //推荐能量
    private float totalCount;
    //实际能量
    private float currentCount;
    //动画需要的
    private float progressCount;
    //比例
    private float scale;
    //背景颜色
    private int bgColor;
    //文字颜色
    private int textColor;
    //进度条颜色
    private int progessColor;
    private Paint bgPaint;
    //背景矩形
    private RectF bgRectF;
    private float radius;
    private int width;
    private int height;
    private PorterDuffXfermode mPorterDuffXfermode;
    private Paint progessPaint;

    private String saleText;
    private String overText;
    private float textSize;

    private Paint textPaint;
    private float baseLineY;


    public EnergyProgressView(Context context) {
        this(context, null);
    }

    public EnergyProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs);
        initPaint();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.SaleProgressView);
        progessColor = ta.getColor(R.styleable.SaleProgressView_sideColor,0xff24C68A);
        textColor = ta.getColor(R.styleable.SaleProgressView_textColor,0xffff3c32);
        bgColor=ta.getColor(R.styleable.SaleProgressView_sideColor,0xff585858);
        overText = ta.getString(R.styleable.SaleProgressView_overText);
        textSize = ta.getDimension(R.styleable.SaleProgressView_textSize,sp2px(16));
        ta.recycle();
    }

    private void initPaint() {
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setStrokeWidth(height);
        bgPaint.setColor(bgColor);

        progessPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progessPaint.setStyle(Paint.Style.FILL);
        progessPaint.setStrokeWidth(height);
        progessPaint.setColor(progessColor);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(textSize);

        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取View的宽高
        width = getMeasuredWidth();
        height = getMeasuredHeight();

        //圆角半径
        radius = height / 2.0f;
        //留出一定的间隙，避免边框被切掉一部分
        if (bgRectF == null) {
            bgRectF = new RectF(0, 0, width, height );
        }

        if (baseLineY == 0.0f) {
            Paint.FontMetricsInt fm = textPaint.getFontMetricsInt();
            baseLineY = height / 2 - (fm.descent / 2 + fm.ascent / 2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        progressCount = currentCount;
        if (totalCount == 0) {
            scale = 0.0f;
        } else {
            scale = Float.parseFloat(new DecimalFormat("0.00").format((float) currentCount / (float) totalCount));
        }

        drawBg(canvas);
        drawFg(canvas);
        drawText(canvas);
    }

    //绘制背景
    private void drawBg(Canvas canvas) {
        canvas.drawRoundRect(bgRectF, radius, radius, bgPaint);
    }

    //绘制进度条
    private void drawFg(Canvas canvas) {
        if (scale == 0.0f) {
            return;
        }
        if (scale>1.0){
            canvas.drawRoundRect(
                    new RectF(0, 0, width , height),
                    radius, radius, progessPaint);
        }
        else {
            if (width*scale<=radius){
                canvas.drawCircle(width*scale/2,height/2,width*scale/2,progessPaint);
            }
            else {
                canvas.drawRoundRect(
                        new RectF(0, 0, width * scale, height),
                        radius, radius, progessPaint);
            }
        }
    }

    //绘制文字信息
    private void drawText(Canvas canvas) {
        String scaleText = new DecimalFormat("#%").format(scale);
        String saleText = String.format(this.saleText+"%s千卡", currentCount);
        String overText=String .format("已超出%s千卡",new DecimalFormat("0.0").format(currentCount-totalCount));

        float scaleTextWidth = textPaint.measureText(scaleText);

        float overTextWidth=textPaint.measureText(overText);
        textPaint.setColor(Color.WHITE);

        if (scale < 1.0f) {
            canvas.drawText(saleText, dp2px(10), baseLineY, textPaint);
            canvas.drawText(scaleText, width - scaleTextWidth - dp2px(10), baseLineY, textPaint);
        }else {
            canvas.drawText(overText, dp2px(10), baseLineY, textPaint);
        }
    }

    private int dp2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int sp2px(float spValue) {
        float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    public void setTotalAndCurrentCount(float totalCount, float currentCount) {
        this.totalCount = totalCount;
        this.currentCount = currentCount;
        postInvalidate();
    }
    public void setprogessColor(int progressColor) {
        this.progessColor = progressColor;
        progessPaint.setColor(progressColor);
        invalidate();
    }
    public void setText(String sale){
        this.saleText=sale;
        postInvalidate();
    }
}
