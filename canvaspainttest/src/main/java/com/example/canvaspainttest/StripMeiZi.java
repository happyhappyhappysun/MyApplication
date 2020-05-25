package com.example.canvaspainttest;
/**
 * https://www.runoob.com/w3cnote/android-tutorial-xfermode-porterduff4.html
 * 先绘制的图片是DST，后绘制的是SRC，然后ontouch方法只要触摸屏幕就会调用，invalidate调用draw方法，每次都要绘制前后图片，然后绘制path，记录划过的路径
 * 将DST与之重叠的位置设置为透明。canvas画布上采取贴图形式，贴的SRC图片，然后画笔在Canvas上绘画。
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class StripMeiZi extends View {

    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private Canvas mCanvas;
    private Bitmap mBeforeBitmap;
    private Bitmap mBackBitmap;
    private int mLastX,mLastY;
    private int screenW, screenH; //屏幕宽高
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    private boolean isFirst = true;
    public StripMeiZi(Context context) {
        this(context, null);
        Log.d("StripMeiZi","StripMeiZi构造函数1调用啦~~~~~~~~~~~~~~~~~");
    }
    public StripMeiZi(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d("StripMeiZi","StripMeiZi构造函数2调用啦~~~~~~~~~~~~~~~~~");
        screenW = ScreenUtil.getScreenW(context);
        screenH = ScreenUtil.getScreenH(context);
        init();
    }

    private void init() {
        //背后图片，这里让它全屏
        mBackBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.meizi_back);
        mBackBitmap = Bitmap.createScaledBitmap(mBackBitmap, screenW, screenH, false);
        //前面的图片，并绘制到Canvas上
        mBeforeBitmap = Bitmap.createBitmap(screenW, screenH, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBeforeBitmap);
        mCanvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
                R.mipmap.meizi_before), null, new RectF(0, 0, screenW, screenH), null);
        //画笔相关的设置
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND); // 圆角
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 圆角
        mPaint.setStrokeWidth(80);    // 设置画笔宽
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("StripMeiZi","onDraw（）方法调用啦~~~~~~~~~~~~~~~~~");
        //目标图
        canvas.drawBitmap(mBackBitmap, 0, 0, null);
        //绘制路径（放在下一行代码的前后都没关系）
        drawPath();
        //源图，与paint路径重合的地方会变透明
        canvas.drawBitmap(mBeforeBitmap, 0, 0, null);

    }

    private void drawPath() {
        mPaint.setXfermode(mXfermode);
        mCanvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("StripMeiZi","onTouchEvent（）方法调用啦~~~~~~~~~~~~~~~~~");
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                Log.d("StripMeiZi","onTouchEvent（）按下方法调用啦~~~~~~~~~~~~~~~~~");
                mLastX = x;
                mLastY = y;
                mPath.moveTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("StripMeiZi","onTouchEvent（）移动方法调用啦~~~~~~~~~~~~~~~~~");

                int dx = Math.abs(x - mLastX);
                int dy = Math.abs(y - mLastY);

                if (dx > 3 || dy > 3)
                    mPath.lineTo(x, y);

                mLastX = x;
                mLastY = y;
                break;
        }
        //调用draw方法
        invalidate();
        return true;
    }
}
