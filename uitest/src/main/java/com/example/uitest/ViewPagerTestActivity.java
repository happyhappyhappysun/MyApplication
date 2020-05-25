package com.example.uitest;
/**
 * 1.简单的viewpager的使用，就是利用pageAdapter来将view的集合和viewpager绑定
 * 2.加入title的viewpager，利用PagerTabStrip和title的集合绑定，重写getPageTitle（）方法即可
 * 3.实现title在一页，通过点击title切换页面的TabHost，（重点计算偏移量，点击第一个之后点击第三个就需要进行两个页面的偏移量）推荐使用fragment实现
 * 详细代码查看菜鸟教程
 * https://www.runoob.com/w3cnote/android-tutorial-viewpager.html
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

public class ViewPagerTestActivity extends AppCompatActivity {
    ViewPager vpager_one;
    //视图集合以及标题集合
    ArrayList<View> aList;
    ArrayList<String> sList;
    viewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_test);
        vpager_one = (ViewPager) findViewById(R.id.vpager_one);

        aList = new ArrayList<View>();
        LayoutInflater inflater = getLayoutInflater();
        aList.add(inflater.inflate(R.layout.view_one,null,false));
        aList.add(inflater.inflate(R.layout.view_two,null,false));
        aList.add(inflater.inflate(R.layout.view_three,null,false));

        sList = new ArrayList<String>();
        sList.add("橘黄");
        sList.add("淡黄");
        sList.add("浅棕");
        adapter = new viewPagerAdapter(aList,sList);
        vpager_one.setAdapter(adapter);

    }
}
