package com.example.uitest;
/**
 * 1.adapter的创建：主要是ViewHolder的创建（就是和子项view关联）+赋值
 * 2.layoutmanager管理布局排列方式。listview是本身管理，所以只能竖着排列，而recycleview可以可以根据不同的布局的manager来定义其不同的排列方式。
 * 3.监听事件--为子项中每一个组件都设置监听事件
 * 4.自定义Toast通知（重点是加载一个布局文件）
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import java.util.ArrayList;

public class RecycleViewTestActivity extends AppCompatActivity {
    private ArrayList<Fruit> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_test);
        initFruit();
        RecyclerView recyclerView = findViewById(R.id.recy_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        //设置布局方式(水平方向)
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);

        //设置瀑布式（网格布局管理器）
        StaggeredGridLayoutManager gmanager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gmanager);

        //recyclerView.setLayoutManager(manager);
        FruitAdapter adapter = new FruitAdapter(mData);
        recyclerView.setAdapter(adapter);
    }

    private void initFruit() {
        for(int i=0;i<2;i++){
            mData.add(new Fruit(R.mipmap.iv_icon_1, "水果1dsdsdsdsdsdsdsds"));
            mData.add(new Fruit(R.mipmap.iv_icon_2, "水果2"));
            mData.add(new Fruit(R.mipmap.iv_icon_3, "水果3"));
            mData.add(new Fruit(R.mipmap.iv_icon_4, "水果4dsdsdsdsdsdsdsdsdsddddddddddddddd"));
            mData.add(new Fruit(R.mipmap.iv_icon_5, "水果5"));
            mData.add(new Fruit(R.mipmap.iv_icon_6, "水果6"));
            mData.add(new Fruit(R.mipmap.iv_icon_7, "水果7"));
        }
    }
}
