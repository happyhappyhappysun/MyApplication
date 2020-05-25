package com.example.fragmenttest;
/**
 * 1.采用LinearLayout + TextView实现了底部导航栏的效果，每次点击我们都要重置
 * 所有TextView的状态，然后选中点击的TextView，有点麻烦是吧，另一种方法：
 * RadioGroup + RadioButton来实现我们上一节的效果
 * 将每一个textview的相同属性抽象出来放在style中
 * 2.动态添加fragment，必须先判断是不是空，才可以add，开启事务，记得提交。
 * if(fg1 == null){
 *                     fg1 = new MyFragment("第一个Fragment");
 *                     fTransaction.add(R.id.ly_content,fg1);
 *                 }else{
 *                     fTransaction.show(fg1);
 *                 }
 * 3.状态资源列表文件(xml)，可以根据控件的状态进行不同外表形式的选择。只需要将控件的属性设置为xml文件即可（例如：控件背景）
 */

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //UI Object
    private TextView txt_topbar;
    private TextView txt_channel;
    private TextView txt_message;
    private TextView txt_better;
    private TextView txt_setting;
    private FrameLayout ly_content;

    //Fragment Object
    private MyFragment fg1,fg2,fg3,fg4;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
            获取四个textview---设置点击事件---首先所有textview设定为未选中状态，选中哪个把哪个设为选中状态---fragment的替换
            （这里看一下怎么使用一个自定义的fragment来实现不同fragment切换，即不同的对象代表不同的fragment）
         */
       // fManager = getSupportFragmentManager();
        fManager = getFragmentManager();
        bindViews();
        txt_channel.performClick();   //模拟一次点击，既进去后选择第一项
    }
    //UI组件初始化与事件绑定
    private void bindViews() {
        txt_topbar = (TextView) findViewById(R.id.txt_topbar);
        txt_channel = (TextView) findViewById(R.id.txt_channel);
        txt_message = (TextView) findViewById(R.id.txt_message);
        txt_better = (TextView) findViewById(R.id.txt_better);
        txt_setting = (TextView) findViewById(R.id.txt_setting);
        ly_content = (FrameLayout) findViewById(R.id.ly_content);

        txt_channel.setOnClickListener(this);
        txt_message.setOnClickListener(this);
        txt_better.setOnClickListener(this);
        txt_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        android.app.FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (v.getId()){
            case R.id.txt_channel:
                setSelected();
                txt_channel.setSelected(true);
                if(fg1 == null){
                    fg1 = new MyFragment("第一个Fragment");
                    fTransaction.add(R.id.ly_content,fg1);
                }else{
                    fTransaction.show(fg1);
                }
                break;
            case R.id.txt_message:
                setSelected();
                txt_message.setSelected(true);
                if(fg2 == null){
                    fg2 = new MyFragment("第二个Fragment");
                    fTransaction.add(R.id.ly_content,fg2);
                }else{
                    fTransaction.show(fg2);
                }
                break;
            case R.id.txt_better:
                setSelected();
                txt_better.setSelected(true);
                if(fg3 == null){
                    fg3 = new MyFragment("第三个Fragment");
                    fTransaction.add(R.id.ly_content,fg3);
                }else{
                    fTransaction.show(fg3);
                }
                break;
            case R.id.txt_setting:
                setSelected();
                txt_setting.setSelected(true);
                if(fg4 == null){
                    fg4 = new MyFragment("第四个Fragment");
                    fTransaction.add(R.id.ly_content,fg4);
                }else{
                    fTransaction.show(fg4);
                }
                break;
        }
        fTransaction.commit();

    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {

            if(fg1 != null)fragmentTransaction.hide(fg1);
            if(fg2 != null)fragmentTransaction.hide(fg2);
            if(fg3 != null)fragmentTransaction.hide(fg3);
            if(fg4 != null)fragmentTransaction.hide(fg4);

    }

//    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
//
//            if(fg1 != null)fragmentTransaction.hide(fg1);
//            if(fg2 != null)fragmentTransaction.hide(fg2);
//            if(fg3 != null)fragmentTransaction.hide(fg3);
//            if(fg4 != null)fragmentTransaction.hide(fg4);
//
//    }

    //设置所有的选中状态为false
    private void setSelected() {
        txt_channel.setSelected(false);
        txt_message.setSelected(false);
        txt_better.setSelected(false);
        txt_setting.setSelected(false);
    }


}
