package com.example.uitest;
/**
 * 1.选项菜单：最为常用，且重写onCreateOptionsMenu（）方法，然后解析xml文件。放在溢出菜单中
 * （在导航栏）
 * 2.上下文菜单：长按某一个view组件就可以唤出，registerForContextMenu(tv_context);记得给组件注册
 * 上下文菜单，位置在组建的额下面
 * 3.子菜单：就是在菜单里面<item>里面加一个<menu><group><item>菜单里面套菜单，但是
 * 子菜单下面不能再套菜单了。
 * 4.popmenu：弹框菜单，和popwindow一样，是一个类，有很多方法，可以加载任意view组件，
 * 需要构造函数创建，然后添加，显示（show）即可。
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MenuTestActivity extends AppCompatActivity {
    //1.定义不同颜色的菜单项的标识:
    final private int RED = 110;
    final private int GREEN = 111;
    final private int BLUE = 112;
    final private int YELLOW = 113;
    final private int GRAY = 114;
    final private int CYAN = 115;
    final private int BLACK = 116;

    TextView tv_test;
    TextView tv_context;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_test);
        tv_test=findViewById(R.id.tv_test);

        tv_context = findViewById(R.id.text);
        registerForContextMenu(tv_context);

        button = findViewById(R.id.btn_show_menu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(MenuTestActivity.this,button);
                menu.getMenuInflater().inflate(R.menu.menu_pop,menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.lpig:
                                Toast.makeText(MenuTestActivity.this,"你点了小猪~",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.bpig:
                                Toast.makeText(MenuTestActivity.this,"你点了大猪~",Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });
                menu.show();
            }
        });
    }

    /*
    选项菜单:采用解析menu菜单资源（xml）的方式，这样更改的话只需要更改xml文件即可
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(1, RED, 4, "红色");
//        menu.add(1, GREEN, 2, "绿色");
//        menu.add(1, BLUE, 3, "蓝色");
//        menu.add(1, YELLOW, 1, "黄色");
//        menu.add(1, GRAY, 5, "灰色");
//        menu.add(1, CYAN, 6, "蓝绿色");
//        menu.add(1, BLACK, 7, "黑色");
//
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case RED:
                tv_test.setTextColor(Color.RED);
                break;
            case GREEN:
                tv_test.setTextColor(Color.GREEN);
                break;
            case BLUE:
                tv_test.setTextColor(Color.BLUE);
                break;
            case YELLOW:
                tv_test.setTextColor(Color.YELLOW);
                break;
            case GRAY:
                tv_test.setTextColor(Color.GRAY);
                break;
            case CYAN:
                tv_test.setTextColor(Color.CYAN);
                break;
            case BLACK:
                tv_test.setTextColor(Color.BLACK);
            case R.id.new_game:
                tv_test.setText("点击了菜单1");
                break;
            case R.id.help:
                tv_test.setText("点击了菜单2");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    上下文菜单
     */

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        MenuInflater inflater = new MenuInflater(this);
//        inflater.inflate(R.menu.menu_context,menu);
//        super.onCreateContextMenu(menu, v, menuInfo);
        //子菜单部分：
        MenuInflater inflator = new MenuInflater(this);
        inflator.inflate(R.menu.menu_sub, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//            case R.id.blue:
//                tv_context.setTextColor(Color.BLUE);
//                break;
//            case R.id.green:
//                tv_context.setTextColor(Color.GREEN);
//                break;
//            case R.id.red:
//                tv_context.setTextColor(Color.RED);
//                break;
//        }
//        return true;

        //子菜单部分：
        switch (item.getItemId()) {
            case R.id.one:
                Toast.makeText(MenuTestActivity.this,"你点击了子菜单一",Toast.LENGTH_SHORT).show();
                break;
            case R.id.two:
                item.setCheckable(true);
                Toast.makeText(MenuTestActivity.this,"你点击了子菜单二",Toast.LENGTH_SHORT).show();
                break;
            case R.id.three:
                Toast.makeText(MenuTestActivity.this,"你点击了子菜单三",Toast.LENGTH_SHORT).show();
                item.setCheckable(true);
                break;
        }
        return true;
    }


}
