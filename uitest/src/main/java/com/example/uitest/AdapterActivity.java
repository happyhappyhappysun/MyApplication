package com.example.uitest;
/**
 * arrayAdapter的使用
 * baseadapter的使用（优化，两步）
 * listview的表头、表尾以及数据的更新
 * 子项多布局
 *
 * listview怎么获取第一项的索引或者根据索引获取子项view。
 *  int visiblePosition = list.getFirstVisiblePosition();
 *  View view = list.getChildAt(index - visiblePosition);
 */

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AdapterActivity extends AppCompatActivity implements View.OnClickListener{
    private ListView list;
    private ListView list_content;
    private AnimalAdapter mAdapter = null;
    private MutiLayoutAdapter myAdapter = null;
    private LinkedList<Animal> mData = null;
    private ArrayList<Object> mdata_1 = null;
    //两种布局的标志位，根据这个分别来处理
    private static final int TYPE_BOOK = 0;
    private static final int TYPE_APP = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);
        arrayadapttest();
        baseadaptertest();
        Button button1 = findViewById(R.id.button_1);
        Button button2 = findViewById(R.id.button_2);
        Button button3 = findViewById(R.id.button_3);
        Button button4 = findViewById(R.id.button_4);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        //item多布局使用
        MutiLayoutTest();
    }

    private void MutiLayoutTest() {
        initData_1();
        list = findViewById(R.id.array1);
        list_content = (ListView) findViewById(R.id.array2);
        myAdapter = new MutiLayoutAdapter(AdapterActivity.this,mdata_1);
        list_content.setAdapter(myAdapter);
        //listview设置监听事件
        list_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AdapterActivity.this,"点击了"+mdata_1.get(position).toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initData_1() {
        //数据准备：
        mdata_1 = new ArrayList<Object>();
        for(int i = 0;i < 20;i++){
            switch ((int)(Math.random() * 2)){
                case TYPE_BOOK:
                    mdata_1.add(new Book("《第一行代码》","郭霖"));
                    break;
                case TYPE_APP:
                    mdata_1.add(new App(R.mipmap.iv_icon_baidu,"百度"));
                    break;
            }
        }    }

    private void baseadaptertest() {
        initData();
        list = findViewById(R.id.array1);

        //给listview加标题（表头）,必须在设置适配器的前面
        View head = LayoutInflater.from(AdapterActivity.this).inflate(R.layout.view_header,null,false);
        list.addHeaderView(head);

        mAdapter = new AnimalAdapter(AdapterActivity.this, mData);
        list.setAdapter(mAdapter);
    }

    private void initData() {
        mData = new LinkedList<Animal>();
        mData.add(new Animal("狗说", "你是狗么?", R.drawable.ic_icon_dog));
        mData.add(new Animal("牛说", "你是牛么?", R.drawable.ic_icon_cow));
        mData.add(new Animal("鸭说", "你是鸭么?", R.drawable.ic_icon_duck));
        mData.add(new Animal("鱼说", "你是鱼么?", R.drawable.ic_icon_fish));
        mData.add(new Animal("马说", "你是马么?", R.drawable.ic_icon_horse));

    }

    private void arrayadapttest() {
        //要显示的数据
        String[] strs = {"基神","B神","翔神","曹神","J神"};
        //创建ArrayAdapter
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,strs);
        ListView listView = findViewById(R.id.array);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_1:
                mAdapter.addData(new Animal("马说", "你是马么?", R.drawable.ic_icon_horse));
                break;
            case R.id.button_2:
                mAdapter.addDataAt(new Animal("马说", "你是马么?", R.drawable.ic_icon_horse), 0);
                break;
            case R.id.button_3:
                mAdapter.delete(0);
                break;
            case R.id.button_4:
                mAdapter.update(list,1,new Animal("马说", "你是马么?", R.drawable.ic_icon_horse));
                break;
            default:
                break;
        }
    }
}
