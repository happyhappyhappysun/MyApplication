package com.example.materialdesigntest;
/**
 * 主要有以下工作：
 * 首先是有标题栏：特殊的，是图片和标题结合的布局
 * 然后就是显示内容
 * 需要在adapter中设置监听事件，发送数据给fruitactivity。完成交互。
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class FruitActivity extends AppCompatActivity {
    public static final String PARTNER_NAME = "partner_name";
    public static final String PARTNER_IMAGE_ID = "partner_image_id";
    public static final String PARTNER_PROFILE_ID = "partner_profile_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);
        //设置标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        String partnerName = intent.getStringExtra(PARTNER_NAME); //海贼名称
        int partnerImageId = intent.getIntExtra(PARTNER_IMAGE_ID,0);//海贼图片id
        int partnerProfileId = intent.getIntExtra(PARTNER_PROFILE_ID,R.string.partner_luffy);//海贼资料id
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView partnerImageView = (ImageView) findViewById(R.id.partner_image_view);
        TextView partnerProfile = (TextView) findViewById(R.id.partner_profile);
        collapsingToolbar.setTitle(partnerName); //设置标题
        Glide.with(this).load(partnerImageId).into(partnerImageView);//设置图片
        String content = getString(partnerProfileId);
        partnerProfile.setText(generateContent(content));//设置内容

    }

    private String generateContent(String content) {
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<100;i++){
            builder.append(content);
        }
        return builder.toString();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                // 返回上一个活动
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
