package com.example.zhou.watch.Hear;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhou on 2017/7/17.
 */

public class MySiri extends View {

    private static final float MIN_AMPLITUDE = 0.0575f;
    private float mPrimaryWidth = 2.0f; //主要线的宽度
    private float mSecondaryWidth = 1.5f;//第二条线的宽度
    private float mAmplitude = MIN_AMPLITUDE; //幅度
    private int mWaveColor = Color.WHITE;
    private int mDensity = 2;   // 密度
    private int mWaveCount = 5; //破浪总数
    private float mFrequency = 0.1875f; //频率
    private float mPhaseShift = -0.1875f; //相移
    private float mPhase = mPhaseShift;

    private Paint mPrimaryPaint;
    private Paint mSecondaryPaint;

    private Path mPath;

    private float mLastX;
    private float mLastY;

    public MySiri(Context context) {
        this(context, null);
    }

    public MySiri(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySiri(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        mPrimaryPaint = new Paint();
        mPrimaryPaint.setStrokeWidth(mPrimaryWidth); /**设置空心线宽**/
        mPrimaryPaint.setAntiAlias(true);
        mPrimaryPaint.setStyle(Paint.Style.STROKE); //描边
        mPrimaryPaint.setColor(mWaveColor);

        mSecondaryPaint = new Paint();
        mSecondaryPaint.setStrokeWidth(mSecondaryWidth);
        mSecondaryPaint.setAntiAlias(true);
        mSecondaryPaint.setStyle(Paint.Style.STROKE);
        mSecondaryPaint.setColor(mWaveColor);

        mPath = new Path();
    }

    public void updateAmplitude(float amplitude) {
        mAmplitude = Math.max(amplitude, MIN_AMPLITUDE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        for (int l = 0; l < mWaveCount; ++l) {
            float midH = height / 2.0f;
            float midW = width / 2.0f;

            float maxAmplitude = midH / 2f - 4.0f;
            float progress = 1.0f - l * 1.0f / mWaveCount;
            float normalAmplitude = (1.5f * progress - 0.5f) * mAmplitude;

            float multiplier = (float) Math.min(1.0, (progress / 3.0f * 2.0f) + (1.0f / 3.0f));

            if (l != 0) {
                mSecondaryPaint.setAlpha((int) (multiplier * 255));
            }

            mPath.reset();
            for (int x = 0; x < width + mDensity; x += mDensity) {
                float scaling = 1f - (float) Math.pow(1 / midW * (x - midW), 2);
                float y = scaling * maxAmplitude * normalAmplitude * (float) Math.sin(
                        180 * x * mFrequency / (width * Math.PI) + mPhase) + midH;
                //canvas.drawPoint(x, y, l == 0 ? mPrimaryPaint : mSecondaryPaint);

                //canvas.drawLine(x, y, x, 2*midH - y, mSecondaryPaint);
                if (x == 0) {
                    mPath.moveTo(x, y);
                } else {
                    mPath.lineTo(x, y);
                    //final float x2 = (x + mLastX) / 2;
                    //final float y2 = (y + mLastY) / 2;
                    //mPath.quadTo(x2, y2, x, y);
                }

                mLastX = x;
                mLastY = y;
            }

            if (l == 0) {
                canvas.drawPath(mPath, mPrimaryPaint);
            } else {
                canvas.drawPath(mPath, mSecondaryPaint);
            }
        }

        mPhase += mPhaseShift;
        invalidate();
    }
}


