package com.example.uitest;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class viewPagerAdapter extends PagerAdapter {
    //用来装view的容器以及用来装标题的集合
    private ArrayList<View> viewLists;
    private ArrayList<String> titleLists;
    public viewPagerAdapter(ArrayList<View> aList, ArrayList<String> sList) {
        this.viewLists = aList;
        this.titleLists = sList;
    }


    //返回视图的个数
    @Override
    public int getCount() {
        return viewLists.size();
    }

    //即它俩是否是对应的，对应的表示同一个View
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    /*
    重点：初始化视图
    将指定位置的view加到视图容器中，然后创建并且显示，返回一个视图或者和视图对应的自定义值
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(viewLists.get(position));
        return viewLists.get(position);
    }
    //没有这个重写方法，那么当滑到最后一个view时就会退出程序
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewLists.get(position));
    }
    //重写title相关的方法

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleLists.get(position);
    }
}
