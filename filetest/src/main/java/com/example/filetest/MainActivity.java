package com.example.filetest;
/**
 * 存储在内存中（）
 * 1.获取内存位置：
 * mContext.getFilesDir().getAbsolutePath();
 * //1.获取当前程序路径
 * getApplicationContext().getFilesDir().getAbsolutePath();
 * //2.获取该程序的安装包路径
 * String path=getApplicationContext().getPackageResourcePath();
 * //3.获取程序默认数据库路径
 * getApplicationContext().getDatabasePath(s).getAbsolutePath();
 * 2.openFileOutput以及openFileInput对内存进行操作，返回FileInputStream流
 * 3.对sd卡操作，需要用到fileoutputstream（）+ 配置权限
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editname;
    private EditText editdetail;
    private Button btnsave;
    private Button btnclean;
    private Button btnread;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        bindViews();
    }
    public void bindViews(){
        editdetail = (EditText) findViewById(R.id.editdetail);
        editname = (EditText) findViewById(R.id.editname);
        btnclean = (Button) findViewById(R.id.btnclean);
        btnsave = (Button) findViewById(R.id.btnsave);
        btnread = (Button) findViewById(R.id.btnread);

        btnclean.setOnClickListener(this);
        btnsave.setOnClickListener(this);
        btnread.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnclean:
                editdetail.setText("");
                editname.setText("");
                break;
            case R.id.btnsave:
//                FileHelper fHelper = new FileHelper(mContext);
                SDFileHelper sdHelper = new SDFileHelper(mContext);
                String filename = editname.getText().toString();
                String filedetail = editdetail.getText().toString();
                try {
//                    fHelper.save(filename, filedetail);
                    sdHelper.savaFileToSD(filename, filedetail);
                    Toast.makeText(getApplicationContext(), "数据写入成功", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "数据写入失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnread:
                String detail = "";
//                FileHelper fHelper2 = new FileHelper(getApplicationContext());
                SDFileHelper sdHelper2 = new SDFileHelper(mContext);
                try {
//                    String fname = editname.getText().toString();
//                    detail = fHelper2.read(fname);
                    String filename2 = editname.getText().toString();
                    detail = sdHelper2.readFromSD(filename2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), detail, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
