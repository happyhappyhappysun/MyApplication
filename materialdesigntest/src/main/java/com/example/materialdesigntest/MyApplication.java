package com.example.materialdesigntest;
/**
 * 获取全局的context对象
 */

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static Context mcontext;

    @Override
    public void onCreate() {
        super.onCreate();
        mcontext = getApplicationContext();
    }
    public static Context getContext(){
        return mcontext;
    }
}
