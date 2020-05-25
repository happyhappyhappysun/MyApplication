package com.example.canvaspainttest;
/**
 * paint一共有18种模式进行图层的层叠效果的展示。这里采用DST_OUT的模式，即不想交的部分绘制目标图形，但是也可以理解为当相交的时候源图形变透明。
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PorterDuffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porter_duff);
    }
}
