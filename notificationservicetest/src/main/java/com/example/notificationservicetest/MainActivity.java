package com.example.notificationservicetest;
/**
 * 任务：服务完成下载工作+notification实现通知效果
 * 1.采用binderservice进行activity和service通信，此时返回的是一个binder，所以主要的任务还是在内部类DownLoadBinder里面实现，
 * 这样通过内部类的实例就可以调用里面的方法啊
 * 2.前台服务：保证服务不会因为内存不够就被杀死
 * 一般前台服务会在状态栏显示一个通知，最典型的应用就是音乐播放器，只要在播放状态下，就算休眠也不会被杀，如果不想显示通知，只要把参数里的int设为0即可
 * 3.通知的知识点：建立，关闭（需要NotificationManager.cancel(1)），使用通知显示下载进度，
 * NotificationCompat.Builder类的使用。可以设置很多属性
 * 首先是获取NotificationManager，然后获取NotificationCompat.Builder.build()一个notification出来，设置相应的属性，最后manager.notify(id,notification)通知。
 * 4.更强大的文件RandomAccessFile，可以跳到具体的位置对文件进行操作。
 * 5.服务启动之后就会在后台一直运行，可以在service中调用downloadtask的实例，从而调用他的方法，所以listener的实例化放在service中，然后将实例对象
 * 以参数的形式传递给downloadtask中使用。然后实现任务到服务的传递参数。（如果没有listener接口，就不能实现任务到服务的传递参数）
 * BindService的启动方式
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DownLoadService.DownLoadBinder downLoadBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_1 = findViewById(R.id.but_1);
        btn_1.setOnClickListener(this);
        Button btn_2 = findViewById(R.id.but_2);
        btn_2.setOnClickListener(this);
        Button btn_3 = findViewById(R.id.but_3);
        btn_3.setOnClickListener(this);
        Intent intent = new Intent(MainActivity.this,DownLoadService.class);
//        startService(intent);
//        bindService(intent,connection,BIND_AUTO_CREATE);
        //直接采用bindservice就可以了，先start后bind，在关闭服务的时候需要同时unbind和stopservice。
        bindService(intent,connection,BIND_AUTO_CREATE);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "拒绝权限将无法使用程序。。。", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downLoadBinder = (DownLoadService.DownLoadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but_1:
                String Url = "http://forspeed.onlinedown.net/down/QQ_PCDownload1100109234.exe";
                downLoadBinder.startDownLoad(Url);
                break;
            case R.id.but_2:
                downLoadBinder.pauseDownLoad();
                break;
            case R.id.but_3:
                downLoadBinder.cancelDownLoad();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
