package com.example.okhttptest;
/**
 * webview可以直接根据URL访问网页，也可以解析html数据来显示网页，loadDataWithBaseURL加载HTML数据
 *
 * 由于子线程是不可以有return语句的，因此一般在网络请求中若开启一个新的子线程的话就使用OKhttp自带的callback回调接口进行数据返回，
 * 同样也可以采用给成员变量赋值的方法，如开始的方法。
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    //直接将要使用的资源赋值成成员变量
    private TextView txtMenu, txtshow;
    private ImageView imgPic;
    private WebView webView;
    private ScrollView scroll;
    private Bitmap bitmap;
    private String detail = "";
    private boolean flag = false;
    private final static String PIC_URL = "https://ww2.sinaimg.cn/large/7a8aed7bgw1evshgr5z3oj20hs0qo0vq.jpg";
    private final static String HTML_URL = "https://www.baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
    }

    private void setViews() {
        txtMenu = (TextView) findViewById(R.id.txtMenu);
        txtshow = (TextView) findViewById(R.id.txtshow);
        imgPic = (ImageView) findViewById(R.id.imgPic);
        webView = (WebView) findViewById(R.id.webView);
        scroll = (ScrollView) findViewById(R.id.scroll);
        //任何view均可以作为上下文菜单使用，需要注册，重写方法onCreateContextMenu()
        registerForContextMenu(txtMenu);
    }

    /*
    重写给上下文菜单赋值的方法，其中，menu指的是textview，因为前面将其注册为上下文菜单。
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menus,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /*
    采用menu菜单资源的时候，注意item的ID的写法：android:id="@+id/one"
     */
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.one:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //请求图片资源并且显示（页面刷新需要在主线程，用到Handler）
                        try {
                            byte[] data = GetData.getImage(PIC_URL);
                            bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(0x01);
                    }
                }).start();
                break;
            case R.id.two:
                //这里可以采用开启子线程的形式，也可以直接采用OKhttp回调接口的形式，方法里面已经封装了开启新子线程处理任务的代码
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            detail = GetData.getHtml(HTML_URL);
//                            Log.d("text",detail+"---------");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        handler.sendEmptyMessage(0x02);
//                    }
//                }).start();
                try {
                    GetData.getHtml(HTML_URL, new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            //该回调方法是在子线程中进行的，所以需要Handler辅助进行UI更新
                            if(response.isSuccessful()){
                                detail = response.body().string();
                                handler.sendEmptyMessage(0x02);
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.three:
                if (detail.equals("")) {
                    Toast.makeText(MainActivity.this, "先请求HTML先嘛~", Toast.LENGTH_SHORT).show();
                } else {
                    handler.sendEmptyMessage(0x03);
                }
                break;
            default:
                break;

        }
        return super.onContextItemSelected(item);
    }
    /*
    刷新页面使用

     */
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0x01:
                    hideAllWidget();
                    imgPic.setVisibility(View.VISIBLE);
                    imgPic.setImageBitmap(bitmap);
                    Toast.makeText(MainActivity.this, "图片加载完毕", Toast.LENGTH_SHORT).show();
                    break;
                case 0x02:
                    hideAllWidget();
                    scroll.setVisibility(View.VISIBLE);
                    txtshow.setText(detail);
                    Toast.makeText(MainActivity.this, "HTML代码加载完毕", Toast.LENGTH_SHORT).show();
                    break;
                case 0x03:
                    hideAllWidget();
                    webView.setVisibility(View.VISIBLE);
                    //两种方式访问网页，可以直接URL或者采用解析HTML形式
                    //webView.loadDataWithBaseURL("",detail,"text/html", "UTF-8", "");

                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.loadUrl("https://www.baidu.com/");

                    Toast.makeText(MainActivity.this, "网页加载完毕", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void hideAllWidget() {
        imgPic.setVisibility(View.GONE);
        scroll.setVisibility(View.GONE);
        webView.setVisibility(View.GONE);
    }
}
