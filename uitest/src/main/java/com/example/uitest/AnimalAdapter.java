package com.example.uitest;
/**
 * 在这里数据mdata之所以选择linkedlist，是因为删除、增加数据较为方便，可与指定删除哪一个。listview数据的更新，重点是对数据的更新
 * 既linkedlist的更新，然后调用notifyDataSetChanged();方法通知界面更细就可以了，不是所有的子项都会更新，而是判断有没有必要全部更新或者
 * 只更新一项即可。
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;

public class AnimalAdapter extends BaseAdapter {
    private Context mContext;
    private LinkedList<Animal> mData;

    public AnimalAdapter(Context mContext, LinkedList<Animal> mData) {
        this.mContext = mContext;
        this.mData = mData;
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

    /*
    增加一条数据
     */
    public void addData(Animal animal){
        if(mData==null){
            mData = new LinkedList<>();
        }
        mData.add(animal);
        notifyDataSetChanged();
    }
    public void addDataAt(Animal animal,int index){
        if(mData==null){
            mData = new LinkedList<>();
        }
        mData.add(index,animal);
        notifyDataSetChanged();
    }

    /*
    删除
     */
    public void delete(int index){
        if(mData!=null){
            mData.remove(index);
        }
        notifyDataSetChanged();
    }
    public void delete(Animal animal){
        if(mData!=null){
            mData.remove(animal);
        }
        notifyDataSetChanged();
    }

    /**
     * 更新，重点是int visiblePosition = list.getFirstVisiblePosition();找到更新的位置。
     * @param list
     * @param index
     */
    public void update(ListView list,int index,Animal animal){
        //更新数据
        mData.set(index,animal);
        int visiblePosition = list.getFirstVisiblePosition();
        View view = list.getChildAt(index - visiblePosition);
        ImageView img = view.findViewById(R.id.image);
        TextView tv1 = (TextView) view.findViewById(R.id.text1);
        TextView tv2 = (TextView) view.findViewById(R.id.text2);
        img.setImageResource(animal.getaIcon());
        tv1.setText(animal.getaName());
        tv2.setText(animal.getaSpeak());
        //这里不要忘记通知界面更新，要不然由于convertView缓存页面的存在，更新的数据还会被原来的数据覆盖。
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        //优化1：减少inflate解析xml的次数，这个是缓存页面
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_animal,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.img_icon = convertView.findViewById(R.id.image);
            viewHolder.txt_content_1 = convertView.findViewById(R.id.text1);
            viewHolder.txt_content_2 = convertView.findViewById(R.id.text2);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        //给每一项的组件赋值
        viewHolder.img_icon.setImageResource(mData.get(position).getaIcon());
        viewHolder.txt_content_1.setText(mData.get(position).getaName());
        viewHolder.txt_content_2.setText(mData.get(position).getaSpeak());
        return convertView;
    }
    //优化2：减少findviewbyid的使用，这是一个组件重用工具，相当于组件的集合
    class ViewHolder{
        ImageView img_icon;
        TextView txt_content_1;
        TextView txt_content_2;
    }
}
