package com.example.canvaspainttest;
/**
 * 设置一张画纸cacheBitmap，两个画家Canvas与cacheCanvas，一支画笔paint，这里之所以要有两位画家，是因为onDraw方法独占Canvas这个类成员。
 * 用户每触摸一次屏幕，都会触发onTouchEvent方法，设置cacheCanvas把用户触摸时绘制的路径放到画纸cacheBitmap上，通过invalidate()方法的调用
 * 再次onDraw方法，Canvas把画纸cacheBitmap放到DrawView这个我们自定义的View上。用户每触摸一次屏幕都会执行一次这个操作。
 *
 * Bitmap是用来装图像，Canvas用来画图像，Paint用来定义绘画的细节（颜色，线条粗细...）
 * 双缓存：就是采用canvas的带参构造函数（Bitmap），那么画的图都存储在Bitmap中，然后再利用canvas的drawBitmap贴图方法，将Bitmap的图画到canvas画布上。
 * 1.menu技术：菜单栏（如何在menu里面嵌套menu）
 * 2.MotionEvent的使用（很重要，但是不太会）event.getAction()可以获取操作的类型（类似抬起，按下以及移动）
 * 每次执行invalidate()方法，都会触发onDraw方法
 * 3.xml文件的布局只是一个自定义view
 * 4.清除：就是让画布颜色置成白色
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PorterDuffActivity.class);
                startActivity(intent);
            }
        });
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoadTestActivity.class);
                startActivity(intent);
            }
        });
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ShaderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        DrawView drawView = findViewById(R.id.drawView1);
        drawView.isEraserModel = false;
        switch (item.getItemId()){
            case R.id.menu1_sub1:
                drawView.paint.setStrokeWidth(1);
                break;
            case R.id.menu1_sub2:
                drawView.paint.setStrokeWidth(5);
                break;
            case R.id.menu1_sub3:
                drawView.paint.setStrokeWidth(10);
                break;
            case R.id.menu1_sub4:
                drawView.paint.setStrokeWidth(50);
                break;
            case R.id.menu2:
                drawView.paint.setColor(Color.BLACK);
                break;
            case R.id.menu3:
                drawView.isEraserModel = true;
                break;
            case R.id.menu4:
                try {
                    drawView.saveBitmap();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.menu5:
                System.exit(0);// 结束程序
                break;
            case R.id.menu6:
                drawView.clearAll();
                break;
            default:
                break;
        }
        return true;
    }
}
