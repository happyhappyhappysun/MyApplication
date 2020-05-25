package com.example.uitest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

public class ProgressBarActivity extends AppCompatActivity {
    ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
        bar = findViewById(R.id.progress_2);
        new Thread(new Runnable() {
            int progress =0;
            @Override
            public void run() {
                while (progress<=100){
                    Message message = Message.obtain();
                    message.what=0x01;
                    message.arg1=progress;
                    handler.sendMessage(message);
                    try {
                        Thread.sleep(1000);
                        progress+=10;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0x01:
                    bar.setProgress(msg.arg1);
                    break;
                default:
                    break;
            }
            return true;
        }
    });

}
