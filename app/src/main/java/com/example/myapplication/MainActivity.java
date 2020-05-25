package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("history","oncreate()......");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("history","onStart()......");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("history","onResume()......");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("history","onPause()......");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("history","onStop()......");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("history","onDestroy()......");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("history","onRestart()......");
    }
}



