package com.example.drawabletest;
/**
 * 采取资源文件形式来设置帧动画设置animation-list中的item
 * 同时也可以采用java代码形式，不推荐
 *
 * drawable资源有13种们可以实现不同的效果，具体看笔记，shape可以用于画图
 */

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imageView_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView_1 = findViewById(R.id.image_1);
        imageView_1.setImageResource(R.drawable.animation);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView_1.getDrawable();
        animationDrawable.start();
    }
}
