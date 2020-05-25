package com.example.test01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MyService extends Service {

    private Callback callback;
    private boolean connecting = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }
    public class Binder extends android.os.Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    /**
     * 采用set方法来回调接口
     * @param callback
     */
    public void setCallback(Callback callback){
        this.callback = callback;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        connecting = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (connecting == true) {
                    i++;
                    if (callback != null) {
                        //注册接口方法，在客户端实现
                        callback.onDataChange(i + "");
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        connecting = false;
    }

}
