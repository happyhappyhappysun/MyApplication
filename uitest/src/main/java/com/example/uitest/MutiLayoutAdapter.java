package com.example.uitest;
/**
 * item多布局，就是说list不是每一个子项的布局都是一个样子，比如聊天界面，一左一右，这种 情况就需要两种布局
 * 两种数据主要用标志位进行区分:
 * 两种viewHOlder重用组件工具
 * 两种数据类型
 * 两种布局界面
 *其实，item多布局的关键就是根据数据的多样性，来匹配相应的item布局来显示就可以了。下面的代码是桥梁。
 * public int getItemViewType(int position) {
 *         if (mData.get(position) instanceof App) {
 *             return TYPE_APP;
 *         } else if (mData.get(position) instanceof Book) {
 *             return TYPE_BOOK;
 *         } else {
 *             return super.getItemViewType(position);
 *         }
 *     }
 *
 * 在value资源文件定义ID，这个重写方法前面参数必须是唯一的；为每一个子项赋于标志位
 * convertView.setTag(R.id.Tag_Book, holder2);
 * getItemViewType（）将数据和标志位匹配到一起。根据数据返回视图的类型。
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MutiLayoutAdapter extends BaseAdapter {
    private ArrayList<Object> mData = null;
    private Context mContext;
    //标志位，用于标志一共几种布局
    //定义两个类别标志
    private static final int TYPE_BOOK = 0;
    private static final int TYPE_APP = 1;

    public MutiLayoutAdapter(AdapterActivity adapterActivity, ArrayList<Object> mdata_1) {
        this.mContext=adapterActivity;
        this.mData=mdata_1;
    }

    /*
    这里是多布局核心代码，listview类型的匹配。一共有几类，怎么判断这类属于哪个标志位
     */
    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof App) {
            return TYPE_APP;
        } else if (mData.get(position) instanceof Book) {
            return TYPE_BOOK;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        if (convertView == null) {
            switch (type) {
                case TYPE_APP:
                    holder1 = new ViewHolder1();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_one, parent, false);
                    holder1.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
                    holder1.txt_aname = (TextView) convertView.findViewById(R.id.txt_aname);
                    //convertView.setTag(R.id.Tag_APP, holder1);
                    convertView.setTag(holder1);
                    break;
                case TYPE_BOOK:
                    holder2 = new ViewHolder2();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_two, parent, false);
                    holder2.txt_bname = (TextView) convertView.findViewById(R.id.txt_bname);
                    holder2.txt_bauthor = (TextView) convertView.findViewById(R.id.txt_bauthor);
                    //convertView.setTag(R.id.Tag_Book, holder2);
                    convertView.setTag(holder2);
                    break;
            }
        } else {
            switch (type) {
                case TYPE_APP:
                    holder1 = (ViewHolder1) convertView.getTag(R.id.Tag_APP);
                    break;
                case TYPE_BOOK:
                    holder2 = (ViewHolder2) convertView.getTag(R.id.Tag_Book);
                    break;
            }

        }
        return convertView;
    }


    //两个不同的ViewHolder
    private static class ViewHolder1 {
        ImageView img_icon;
        TextView txt_aname;
    }

    private static class ViewHolder2 {
        TextView txt_bname;
        TextView txt_bauthor;
    }

}