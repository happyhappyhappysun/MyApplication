package com.example.notificationservicetest;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.webkit.DownloadListener;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.File;

public class DownLoadService extends Service {
    //采用binderservice进行activity和service通信，此时返回的是一个binder，所以主要的任务还是在内部类里面实现，这样通过内部类的实例就可以调用里面的方法啊
    private DownLoadBinder mbinder = new DownLoadBinder();
    private DownLoadTask downLoadTask;
    private String downLoadUrl;
    private DownLoadListener listener = new DownLoadListener() {
        @Override
        public void onSuccess() {
            //结束工作，任务置空，前台服务关闭
            downLoadTask=null;
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("DownLoad  SUCCESS",-1));
            Toast.makeText(DownLoadService.this,"DownLoad  SUCCESS",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            //结束工作，任务置空，前台服务关闭
            downLoadTask=null;
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("DownLoad  FAILED",-1));
            Toast.makeText(DownLoadService.this,"DownLoad  FAILED",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPause() {
            downLoadTask=null;
            Toast.makeText(DownLoadService.this,"DownLoad  PAUSED",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            downLoadTask=null;
            stopForeground(true);
            Toast.makeText(DownLoadService.this,"DownLoad  CANCLED",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProcess(int progress) {
            //实现通知
            getNotificationManager().notify(1,getNotification("DownLoading....",progress));
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mbinder;
    }

    class DownLoadBinder extends Binder{
        public void startDownLoad(String url){
            if(downLoadTask==null){
                downLoadUrl = url;
                downLoadTask = new DownLoadTask(listener);
                downLoadTask.execute(downLoadUrl);//开始下载
                startForeground(1,getNotification("DownLoading....",0));
            }
        }
        public void pauseDownLoad(){
            if(downLoadTask!=null){
                downLoadTask.pauseDownLoad();
            }
        }
        public void cancelDownLoad(){
            if(downLoadTask!=null){
                downLoadTask.cancelDownLoad();
            }else{
                //删除已经下载的文件
                if(downLoadUrl!=null){
                    String fileName = downLoadUrl.substring(downLoadUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory+fileName);
                    if(file.exists()){
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownLoadService.this,"Canceled",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 获取通知管理者，关闭通知
     * @return
     */
    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    /**
     * 获取通知，进行显示（这里采取的是前台服务）
     * 一般前台服务会在状态栏显示一个通知，最典型的应用就是音乐播放器，只要在播放状态下，就算休眠也不会被杀，如果不想显示通知，只要把参数里的int设为0即可
     * @return
     */
    private Notification getNotification(String title, int progress) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(title);
        builder.setContentText("app下载文件");
        builder.setSmallIcon(R.drawable.better);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.img_cat));
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pd = PendingIntent.getActivity(this,0,intent,0);
        builder.setContentIntent(pd);
        //通知显示下载进度
        if(progress>=0){
            builder.setContentText(progress+"%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();
    }
}
