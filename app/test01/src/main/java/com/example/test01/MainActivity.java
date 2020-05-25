package com.example.test01;
/*
实现service与Activity之间的交互：采用回调接口方式
接口定义具体的方法
service注册接口并且传入想要传递的数据
activity实现接口方法（回调接口），取出来service传入的数据，并且可以不断更新中
https://www.cnblogs.com/jiangzhaowei/p/11341890.html
https://www.cnblogs.com/ivan-xu/p/4069479.html多个activity交互（换成list集合接口）
 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    private TextView tvOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        tvOut = (TextView) findViewById(R.id.tvOut);
        findViewById(R.id.btnBindService).setOnClickListener(this);
        findViewById(R.id.btnEndService).setOnClickListener(this);

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        MyService.Binder binder = (MyService.Binder) service;
        MyService myService = binder.getService();
        myService.setCallback(new Callback() {
            @Override
            public void onDataChange(String data) {
                Message msg = new Message();
                msg.obj = data;
                handler.sendMessage(msg);
            }
        });

    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tvOut.setText(msg.obj.toString());
        }
    };

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBindService:
                bindService(new Intent(this, MyService.class), this, BIND_AUTO_CREATE);
                break;
            case R.id.btnEndService:
                unbindService(this);
                break;
            default:
                break;
        }

    }
}
