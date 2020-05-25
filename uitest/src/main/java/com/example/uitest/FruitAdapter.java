package com.example.uitest;
/**
 * 获取context的方法：(总结一下)
 * 1.activity.class
 * 2.getContext()
 * public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
 *         //获取子项view布局
 *         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_test,parent,false);
 *         ViewHolder holder = new ViewHolder(view);
 *         return holder;
 *     }
 */

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FruitAdapter extends RecyclerView.Adapter <FruitAdapter.ViewHolder>{
    private ArrayList<Fruit> mData ;
    public FruitAdapter(ArrayList<Fruit> mData) {
        this.mData = mData;
    }

    //创建ViewHolder的实例（即子项view布局和holder绑定）
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        //获取子项view布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_test,parent,false);
        ViewHolder holder = new ViewHolder(view);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //自定义toast显示
                Toast toast = new Toast(parent.getContext());
                View view = LinearLayout.inflate(parent.getContext(),R.layout.item_recycle_test,null);
                ImageView imageView = view.findViewById(R.id.image);
                TextView textView = view.findViewById(R.id.text);
                imageView.setImageResource(R.mipmap.iv_icon_2);
                textView.setText("点击了子空间中的text");
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(view);
                toast.show();
                //Toast.makeText(parent.getContext(),"点击了子空间中的text",Toast.LENGTH_LONG).show();
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(),"点击了子空间中的image",Toast.LENGTH_LONG).show();
            }
        });
        return holder;
    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//    }

    //对子项数据进行赋值，在每一项子项刷新出来的时候被调用
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit = mData.get(position);
        holder.imageView.setImageResource(fruit.getaIcon());
        holder.textView.setText(fruit.getaName());
    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    //重复组件（给子项view赋值）
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text);
        }
    }
}
