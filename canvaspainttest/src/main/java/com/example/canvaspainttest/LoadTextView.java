package com.example.canvaspainttest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Jay on 2015/10/26 0026.
 * 实现动图的效果：mDynamicRect.top = mCurTop;不断对矩形的高进行调整，然后调用invalidate，可调用ondraw方法，刷新画布，即实现了动态的效果。
 * 可以采用改变图形的宽，高，坐标等，然后invalidate调用ondraw，实现一个自身的循环调用，动态效果。
 * mCurTop += 2;
 *         if (mCurTop >= mBitH) {
 *             mCurTop = 0;
 *         }
 *         mDynamicRect.top = mCurTop;
 *         invalidate();    //重绘
 */
public class LoadTextView extends View {


    private PorterDuffXfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    private Bitmap backBitmap;
    private Paint mPaint;
    private int mBitW, mBitH;
    private int mCurW, mCurH, mCurTop;
    private Rect mDynamicRect;


    public LoadTextView(Context context) {
        this(context, null);
    }

    public LoadTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCurW = ScreenUtil.getScreenW(context);
        mCurH = ScreenUtil.getScreenH(context);
        init();
    }

    public LoadTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void init() {

        //画笔初始化：
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setFilterBitmap(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);

        //背部图片的初始化
        backBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_string);
        mBitH = backBitmap.getHeight();
        mBitW = backBitmap.getWidth();

        //设置当前的高度
        //mCurTop = mBitH;
        mCurTop = 0;
        mDynamicRect = new Rect(0, mBitH, mBitW, mBitH);  //初始化原图
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saveLayerCount = canvas.saveLayer(0, 0, mCurW, mCurH, mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(backBitmap, 0, 0, mPaint);// 绘制目标图
        mPaint.setXfermode(mXfermode);    //设置混排模式
        canvas.drawRect(mDynamicRect, mPaint);   //绘制源图
        mPaint.setXfermode(null);         //清除混排模式
        canvas.restoreToCount(saveLayerCount);    //恢复保存的图层

        // 改变Rect区域，（从下往上）
//        mCurTop -= 2;
//        if (mCurTop <= 0) {
//            mCurTop = mBitH;
//        }
        //绘制不同大小的矩形（从上往下）
        mCurTop += 2;
        if (mCurTop >= mBitH) {
            mCurTop = 0;
        }
        mDynamicRect.top = mCurTop;
        invalidate();    //重绘
    }

}

