package com.example.materialdesigntest;
/**
 * 1.toolbar:相比于actionbar，可以设置按钮（带图标）,首先设置style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar"
 * toolbar中菜单项的使用，设置app:showAsAction="always"none，ifroom三项决定菜单项是否显示在toolbar上。
 * setSupportActionBar(toolbar);Activity中设置actionbar为toolbar。其实就是加强版的actionbar
 * HomeAsUp按钮就是导航栏最左侧的按钮，也是菜单选项，他的点击事件可以被重写的方法监听到
 * 相比于actionbar。toolbar可以响应recycleview滚动事件来消失和显示
 * 2.滑动侧边栏：就是一个布局，里面第一个子控件就是主布局，第二个子控件就是侧边栏
 * 设置tools:openDrawer="start"。可以实现侧边栏从那边出来（left和right）
 * 3.NavigationView使用：使得侧边栏更美观，使用了一个圆形工具开源项目（显示圆形图片）
 * 准备一个menu菜单项(显示菜单)(设置菜单项只能单选（放在group里面）)和一个头部布局（一个头像，一个名字，一个邮箱）
 * 开源项目de.hdodenhof.circleimageview.CircleImageView的使用就是一个imageview一样，就是封装了方法，将图片圈化显示
 * 接下来就是在mainactivity中获取该view，然后设置点击事件,注意：点击之后实现关闭侧边栏。
 * android.R.id.home是HomeAsUp按钮的ID，不要忘了Android
 * 4.悬浮按钮，FloatingActionButton,采用立体的效果，他可以点击，有监听事件，可以设置悬浮高度，从而影响阴影，相当于投影
 * app:layout_anchor="@id/appBar"
 * app:layout_anchorGravity="bottom|end"
 * 用于设置描点，就是具体在哪块区域显示，这里设置在标题栏appbarlayout中，位置在右边下边。
 * 5.Snackbar，高级的toast，可以点击的toast，用户可进行交互,不要忘了他也得需要进行show（）
 * 6.CoordinatorLayout高级的frameLayout布局。他可以监听所有的子控件，合理的安排他们的位置，不至于某个控件被遮盖。（即让悬浮按钮上移）
 * 由于Snackbar.make(v,..)传入第一个参数是悬浮按钮，该按钮是CoordinatorLayout的子控件，所以可以监听到他的事件。
 * 7.CardView实现卡片式布局效果的重要控件，也同样是一个布局，应用到RecyclerView的子项布局中，让每一个子项看起来都像一个卡片
 * 它也是一个 FrameLayout，只是额外提供了圆角和阴影等效果，有立体感
 * Glide用到的开源项目，处理比较大的图片不仅可以加载本地图片，还可以加载网络图片，动态图片等。比较强大的功能
 * 8.由于CoordinatorLayout高级的frameLayout布局，但是仍然是frameLayout,没有具体的布局位置限制，所有子控件都会从左上角开始摆设。
 * 出现了AppBarLayout，对toolbar进行设置，内部有很多封装，可以很灵活的调整toolbar的位置，包括很多滚动事件的封装
 * 只需两步就可以解决前面的遮挡问题
 * 第一步是将 Toolbar 嵌套到 AppBarLayout 中
 * 第二步给 RecyclerView 指定一个布局行为
 * 另外还有一些toolbar随着滚动页面时而出现时而消失的效果。使当 RecyclerView 向上滚动时，Toolbar 隐藏，向下滚动时显示
 * app:layout_scrollFlags="scroll|enterAlways|snap"其中 scroll 指当 RecyclerView 向上滚动时，Toolbar 会跟着一起向上滚动并隐藏
 * enterAlways 指向下滚动时一起向下滚动并显示，snap 指当 Toolbar 还没有完全隐藏或显示时，会根据当前滚动的距离自动选择隐藏还是显示
 * 9.下拉刷新：（也是一个布局）SwipeRefreshLayout，在 RecyclerView 的外面嵌套一层 SwipeRefreshLayout。
 * 注意：上面的AppBarLayout实现toolbar不被隐藏时需要给RecyclerView 指定一个布局行为，但是现在RecyclerView 外面嵌套了一层，因此，需要把布局行为给到SwipeRefreshLayout
 * initPartner();
 * adapter.notifyDataSetChanged();
 * swipeRefresh.setRefreshing(false);
 * 刷新的核心代码，采取开启线程休眠2s，然后重新加载数据，adapter题型刷新（数据发生改变），RecyclerView自带刷新功能，但是不太好看。
 * 10.collapsingToolbar.setTitle(partnerName); //设置标题:
 * 接下来就是点进去某一项子项之后的效果图，包括折叠式标题栏，就是便提篮会随着滚动消失。
 * collapsingToolbar是toolbar基础之上的布局，也就是一个布局，有很多功能的布局，主要实现折叠标题栏的效果。
 * 11.NestedScrollView:在 ScrollView 基础上增加了嵌套响应滚动事件的功能，内部只能放一个直接子布局.
 * 12.获取全局context的方法。
 * <application
 *         android:name=".MyApplication"/>
 * 一个应用只能配置一个application，然后利用mcontext = getApplicationContext();获取context，最后提供全局接口供调用。
 *
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mdrawerLayout;
    private NavigationView mNavView;

    private List<Partner> partnerList = new ArrayList<>();

    private PartnerAdapter adapter;

    private SwipeRefreshLayout swipeRefresh;// 刷新

    private Context mcontext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext = MyApplication.getContext();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mdrawerLayout = findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            //这里获取的是toolbar实例，然后设置最左侧的按钮，就是叫做HomeAsUp按钮
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }
        mNavView = findViewById(R.id.nav_view);
        mNavView.setCheckedItem(R.id.nav_menu_call);//将Call菜单设置为默认选中
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mdrawerLayout.closeDrawers();//关闭DrawerLayout滑动菜单
                return true;
            }
        });

        //悬浮按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"you click FloatingActionButton",Toast.LENGTH_SHORT).show();
                Snackbar.make(v,"删除数据",Snackbar.LENGTH_SHORT)
                        .setAction("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mcontext,"you click FloatingActionButton",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });


        //滚动视图显示卡片
        initPartner();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_one_piece);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PartnerAdapter(partnerList);
        recyclerView.setAdapter(adapter);


        // 下拉刷新
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);//设置刷新进度条颜色
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 处理刷新逻辑
                 refreshPartner();
            }
        });

        //获取天气数据https://api.heweather.net/s6/weather/now?location=beijing&key=xxx


    }

    private void refreshPartner() {
        /*
        主要就是利用线程休眠模拟刷新进度条，然后回到主线程，题型receclyview刷新，关闭刷新进度条
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initPartner();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initPartner() {
        partnerList.clear();
        for (int i = 0;i < 50 ;i++){
            Random random = new Random();
            int index = random.nextInt(partners.length);
            partnerList.add(partners[index]);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.backup:
                Toast.makeText(MainActivity.this,"you click backup",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(MainActivity.this,"you click delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(MainActivity.this,"you click settings",Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                mdrawerLayout.openDrawer(Gravity.LEFT);
                break;
            default:
                break;
        }
        return true;
    }

    private Partner[] partners = {
            new Partner("路飞",R.drawable.img1,R.string.partner_luffy),
            new Partner("索隆",R.drawable.img2,R.string.partner_zoro),
            new Partner("山治",R.drawable.img3,R.string.partner_sanji),
            new Partner("艾斯",R.drawable.img4,R.string.partner_ace),
            new Partner("罗",R.drawable.img5,R.string.partner_law),
            new Partner("娜美",R.drawable.img6,R.string.partner_nami),
            new Partner("罗宾",R.drawable.img7,R.string.partner_robin),
            new Partner("薇薇",R.drawable.img1,R.string.partner_vivi),
            new Partner("蕾贝卡",R.drawable.img2,R.string.partner_rebecca),
            new Partner("汉库克",R.drawable.img5,R.string.partner_hancock)};

}
