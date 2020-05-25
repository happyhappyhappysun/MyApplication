package com.example.handlertest;
/**
 * 子线程处理耗时循环的操作，比如说实现进度条更新、不断重复打印某些代码
 * Handler在主线程中更新UI
 * 主线程自动帮我们创建Looper，要是在子线程中利用Handler来sendmessage，则需要Looper.prepare()以及Looler.loop()
 * 这里是实现更新进度条，采用的是sendmessage的方式
 *
 * message可以携带很多数据，当数据比较复杂时，可以采用bundle携带数据，类似map，键值对形式
 *
 * 另外。Handler还可以定点启动线程，采用post方式发送线程，仍然和Handler绑定的线程是同意个线程。也可以采用不断将自己的线程.post(Runnable r)到线程队列，
 * 当run方法执行完事之后线程就会被销毁，重新从线程队列中拿线程来执行，当已经执行完事，removeCallbacks(Runnable r);将线程remove掉。不会执行start方法。
 * https://www.cnblogs.com/vijozsoft/p/5636399.html（推送runnable线程的实例，循环打印信息）
 *
 * handler使用了post方法启动了runnbale，其实启动的线程和activity主线程是同一个线程，因为它只是运行了线程的run方法，而不是start方法
 *
 * https://blog.csdn.net/wsq_tomato/article/details/80301851
 * 使用obtain获取Message对象是因为Message内部维护了一个数据缓存池，回收的Message不会被立马销毁，而是放入了缓存池，
 * 在获取Message时会先从缓存池中去获取，缓存池为null才会去创建新的Message
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start = findViewById(R.id.start);
        final ProgressBar bar = findViewById(R.id.progress_horizontal);
        //打印Activity线程信息
        Log.d("test",Thread.currentThread().getName()+"....."+Thread.currentThread().getId());

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                bar.setMax(100);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test",Thread.currentThread().getName()+"....."+Thread.currentThread().getId());
                        int i =0;
                        while(i<=100){
                            Message message = Message.obtain();
                            message.arg1=i;

                            //携带复杂数据
                            Bundle bundle = new Bundle();
                            bundle.putString("name","jaja");
                            bundle.putInt("temperature", 34);
                            message.setData(bundle);

                            handler.sendMessage(message);
                            i+=10;
                            try {
                                Thread.sleep(1000);
                            }catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
        handler =new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String name = bundle.getString("name");
                bar.setProgress(msg.arg1);   //在handleMessage中处理消息队列中的消息
            }
        };
    }



}
