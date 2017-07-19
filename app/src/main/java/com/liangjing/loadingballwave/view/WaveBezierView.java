package com.liangjing.loadingballwave.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.liangjing.loadingballwave.R;
import com.liangjing.loadingballwave.util.DimentionUtil;

/**
 * Created by liangjing on 2017/7/15.
 * 目的：Loading小球--波浪效果
 * 方法：1、借助三角函数 2、借助Bezier曲线 3、借助绘图工具 4、借助属性动画ValueAnimator
 */

public class WaveBezierView extends View implements View.OnClickListener {

    //波浪路径及内部填充画笔
    private Paint mPaint;
    //文字画笔
    private Paint textPaint;
    //宽高
    private int mWidth = DimentionUtil.dip2px(getContext(), 50);
    private int mHeight = DimentionUtil.dip2px(getContext(), 50);
    //闭合波浪路径
    private Path path;
    //当前属性动画的进度值
    private float currentPercent;
    //颜色
    private int color;
    //设置text显示的默认值为等
    private String text = "等";
    //设置文字尺寸
    private int textSize;
    //属性动画（计值器）
    private ValueAnimator mValueAnimator;

    public WaveBezierView(Context context) {
        this(context, null);
    }

    public WaveBezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public WaveBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 进行初始化操作
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        //获取自定义属性的值(颜色和文字)
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveBezierView);
        color = typedArray.getColor(R.styleable.WaveBezierView_color, Color.rgb(41, 163, 254));
        text = (String) typedArray.getText(R.styleable.WaveBezierView_text);

        //波浪图形及路径填充画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);
        mPaint.setDither(true);

        //文字画笔
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);

        //文字大小
        textSize = DimentionUtil.sp2px(getContext(), 50);

        //闭合波浪路径
        path = new Path();
        setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //底层的字
        textPaint.setColor(color);
        drawCenterText(canvas, textPaint, text);
        //上层的字
        textPaint.setColor(Color.WHITE);
        //生成闭合波浪路径
        path = getWavePath(currentPercent);

        //裁剪文字
        canvas.clipPath(path);
        //裁剪成圆形（src源像素）
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //画波浪(dst目标像素)
        canvas.drawPath(path, mPaint);
        mPaint.setXfermode(null);
        drawCenterText(canvas, textPaint, text);
    }


    /**
     * 目的：生成闭合波浪路径
     *
     * @return
     */
    private Path getWavePath(float percent) {
        Path path = new Path();
        int x = -mWidth;
        //当前x点坐标（根据动画进度水平推移，一个动画周期的距离为一个mWidth）
        x += percent * mWidth;
        //波形的起点
        path.moveTo(x, mHeight / 2);
        //控制点的相对宽度
        int quadWidth = mWidth / 4;
        //控制点的相对高度
        int quadHeight = mHeight / 20 * 3;
        //第一个周期
        path.rQuadTo(quadWidth, quadHeight, quadWidth * 2, 0);
        path.rQuadTo(quadWidth, -quadHeight, quadWidth * 2, 0);
        //第二个周期
        path.rQuadTo(quadWidth, quadHeight, quadWidth * 2, 0);
        path.rQuadTo(quadWidth, -quadHeight, quadWidth * 2, 0);
        //右侧的封闭直线
        path.lineTo(x + mWidth * 2, mHeight);
        //下边的封闭直线
        path.lineTo(x, mHeight);
        //自动闭合补出左边的直线
        path.close();
        return path;
    }

    /**
     * 目的：将文字绘制在控件正中心
     *
     * @param canvas
     * @param textPaint
     * @param text
     */
    private void drawCenterText(Canvas canvas, Paint textPaint, String text) {
        Rect rect = new Rect(0, 0, mWidth, mHeight);
        textPaint.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;

        int centerY = (int) (rect.centerY() - top / 2 - bottom / 2);

        canvas.drawText(text, rect.centerX(), centerY, textPaint);
    }

    /**
     * 点击事件
     *
     * @param //
     */
    @Override
    public void onClick(View v) {
        mValueAnimator = ValueAnimator.ofFloat(0f, 1f);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPercent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }
        setMeasuredDimension(mWidth, mHeight);
        textSize = mWidth / 2;
        textPaint.setTextSize(textSize);
    }

}
