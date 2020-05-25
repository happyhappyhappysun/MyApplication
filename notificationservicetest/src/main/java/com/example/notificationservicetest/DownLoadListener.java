package com.example.notificationservicetest;

/**
 * 接口回调：用于返回结果，实例化该Listener就必须要实现对应的方法
 */
public interface DownLoadListener {
    void onSuccess();
    void onFailed();
    void onPause();
    void onCanceled();
    void onProcess(int progress);
}
