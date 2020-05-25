package com.example.broadcasttest;
/**
 * 1.管理所有的activity
 *
 * 2.使用广播，不管此时处于哪个activity，均可以执行同一个操作，例如弹出相同的一个下线弹框
 * 在这里注册接收器的地方是onresume()就是栈顶activity（因为onresume方法是activity显示的时候调用）
 * 自定义baseactivity，管理所有的activity。
 */

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat .app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.buton1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.content");
                //intent.setAction("hahah");
                Log.d("test","发送广播啦~~");
                sendBroadcast(intent);
                Log.d("test","广播发送成功啦~~");
            }
        });
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("com.example.content");
//        MyReceiver receiver = new MyReceiver();
//        registerReceiver(receiver,intentFilter);
    }

    private ForceReceiver receiver;
    //此时活动在返回栈的栈顶（既只让栈顶的活动注册接收器）
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.content");
        receiver = new ForceReceiver();
        registerReceiver(receiver,intentFilter);
    }

    class ForceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            Log.d("test","接收到广播啦~~");
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("warning....");
        builder.setMessage("下线~~~~");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCollector.finishAll();
                Intent intent = new Intent(context,LoginActivity.class);
                context.startActivity(intent);
            }
        });
        builder.show();
        }
    }
}
