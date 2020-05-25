package com.example.canvaspainttest;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class DrawView extends View {
    private Bitmap cacheBitmap;// 画纸
    private Canvas cacheCanvas;// 创建画布、画家
    private Path path,eraserPath;// 绘图的路径
    public Paint paint,eraserPaint;// 画笔(橡皮擦)
    private float preX, preY,mLastX,mLastY;// 之前的XY的位置，用于下面的手势移动
    private int view_width, view_height;// 屏幕的高度与宽度
    private Context mcontext;
    public boolean isEraserModel;
    /**
     * 完成初始化操作
     * @param context
     */
    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        paint = new Paint();
        cacheCanvas = new Canvas();
        view_width = context.getResources().getDisplayMetrics().widthPixels;
        view_height = context.getResources().getDisplayMetrics().heightPixels;
        //建立和canvas一般大的缓存Bitmap来装图像,建立图像缓冲区用来保存图像
        cacheBitmap = Bitmap.createBitmap(view_width,view_height, Bitmap.Config.ARGB_8888);
        cacheCanvas.setBitmap(cacheBitmap);
        cacheCanvas.drawColor(Color.WHITE);
        paint.setColor(Color.BLACK);// 设置画笔的默认颜色
        paint.setStyle(Paint.Style.STROKE);// 设置画笔的填充方式为无填充、仅仅是画线
        paint.setStrokeWidth(1);// 设置画笔的宽度为1
        mcontext = context;

        //橡皮擦
        isEraserModel=false;
        eraserPaint = new Paint(paint);
        eraserPaint.setStrokeWidth(50f);
        eraserPaint.setColor(Color.WHITE);
        eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(cacheBitmap, 0, 0, paint);// 把cacheBitmap画到DrawView上
    }

    /**
     * 只要触摸屏幕就会触发该事件
     * MotionEvent的使用
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEraserModel) {
            commonTouchEvent(event);
        } else {
            eraserTouchEvent(event);
        }
        invalidate();
        return true;
    }

    /**
     * 这里采用存储在内存中
     * @throws Exception
     */
    public void saveBitmap() throws Exception {
        /*
        这里采用的是存储到SD卡上
         */
//        String sdpath = Environment.getExternalStorageDirectory()
//                .getAbsolutePath();// 获取sdcard的根路径
//        String filename = new SimpleDateFormat("yyyyMMddhhmmss",
//                Locale.getDefault())
//                .format(new Date(System.currentTimeMillis()));// 产生时间戳，称为文件名
//        File file = new File(sdpath + File.separator + filename + ".png");
//        file.createNewFile();
//        FileOutputStream fileOutputStream = new FileOutputStream(file);

        //由于Android是基于Linux的，所以要加上操作模式。
        FileOutputStream fileOutputStream = mcontext.openFileOutput("data",Context.MODE_PRIVATE);
        cacheBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);// 以100%的品质创建png
//        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
//        writer.write();
        // 人走带门
        fileOutputStream.flush();
        fileOutputStream.close();
        Toast.makeText(getContext(),
                "图像已保存到data.png",
                Toast.LENGTH_SHORT).show();

    }
    public void clearAll(){
        cacheCanvas.drawColor(Color.WHITE);
        invalidate();
    }

    /**
     * 普通画笔事件
     */
    public void commonTouchEvent(MotionEvent event){
        // 获取触摸位置
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {// 获取触摸的各个瞬间
            case MotionEvent.ACTION_UP:
                path.reset();
                break;
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);// 绘图的起始点
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - preX);
                float dy = Math.abs(y - preY);
                if (dx > 5 || dy > 5) {// 用户要移动超过5像素才算是画图，免得手滑、手抖现象
                    path.quadTo(preX, preY, (x + preX) / 2, (y + preY) / 2);
//                    path.lineTo(x, y);
                    preX = x;
                    preY = y;
                    cacheCanvas.drawPath(path, paint);// 绘制路径
                }
                break;
        }
    }
    /**
     * 橡皮擦事件
     */
    private void eraserTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //路径
                eraserPath = new Path();
                mLastX = x;
                mLastY = y;
                eraserPath.moveTo(mLastX, mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - mLastX);
                float dy = Math.abs(y - mLastY);
                if (dx >= 3 || dy >= 3) {//绘制的最小距离 3px
                    eraserPath.quadTo(mLastX, mLastY, (mLastX + x) / 2, (mLastY + y) / 2);
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                cacheCanvas.drawPath(eraserPath, eraserPaint);//将路径绘制在mBitmap上
                eraserPath.reset();
                eraserPath = null;
                break;
        }
    }
}
