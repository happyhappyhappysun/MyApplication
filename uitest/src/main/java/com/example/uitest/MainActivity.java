package com.example.uitest;
/**
 * 1.arrayadapter的使用，或者直接采用entries属性
 * 2.baseAdapter的使用
 * 3.item多布局怎么实现（数据模型的多样性匹配不同的item布局来实现）
 * 4.listview的表头、表尾以及数据的更新（即装数据的LinkedList的更新）
 * 5.Adapter的抽象工具类的使用（GridView 中使用了）
 *
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button_1 = findViewById(R.id.button_1);
        Button button_2 = findViewById(R.id.button_2);
        Button button_3 = findViewById(R.id.button_3);
        Button button_4 = findViewById(R.id.button_4);
        Button button_5 = findViewById(R.id.button_5);
        Button button_6 = findViewById(R.id.button_6);
        Button button_7 = findViewById(R.id.button_7);
        Button button_8 = findViewById(R.id.button_8);
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);
        button_7.setOnClickListener(this);
        button_8.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_1:
                Intent intent1 = new Intent(MainActivity.this,ProgressBarActivity.class);
                startActivity(intent1);
                break;
            case R.id.button_2:
                Intent intent2 = new Intent(MainActivity.this,AdapterActivity.class);
                startActivity(intent2);
                break;
            case R.id.button_3:
                Intent intent3 = new Intent(MainActivity.this,AdapterActivity.class);
                startActivity(intent3);
                break;
            case R.id.button_4:
                Intent intent4 = new Intent(MainActivity.this,GridViewTestActivity.class);
                startActivity(intent4);
                break;
            case R.id.button_5:
                Intent intent5 = new Intent(MainActivity.this,RecycleViewTestActivity.class);
                startActivity(intent5);
                break;
            case R.id.button_6:
                Intent intent6 = new Intent(MainActivity.this,AlertDialogTestActivity.class);
                startActivity(intent6);
                break;
            case R.id.button_7:
                Intent intent7 = new Intent(MainActivity.this,MenuTestActivity.class);
                startActivity(intent7);
                break;
            case R.id.button_8:
                Intent intent8 = new Intent(MainActivity.this,ViewPagerTestActivity.class);
                startActivity(intent8);
                break;
            default:
                break;
        }

    }
}
