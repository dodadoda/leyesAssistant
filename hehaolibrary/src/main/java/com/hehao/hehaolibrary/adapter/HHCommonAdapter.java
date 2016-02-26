package com.hehao.hehaolibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hehao.hehaolibrary.bean.HHViewHolder;

import java.util.List;

/**
 * @author HeHao
 * @time 2015/11/6 17:52
 * @email 139940512@qq.com
 */
public abstract class HHCommonAdapter<T> extends BaseAdapter{
    protected Context mContext ;
    protected List<T> mData ;
    protected LayoutInflater mInflater ;
    protected int mLayoutId ;

    public HHCommonAdapter(Context context, List<T> data, int layoutId) {
        this.mContext = context ;
        this.mData = data ;
        mLayoutId = layoutId ;
        mInflater = LayoutInflater.from(context) ;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HHViewHolder holder = HHViewHolder.get(mContext, convertView, parent, mLayoutId, position) ;

        convert(holder, getItem(position)) ;

        return holder.getConvertView();
    }

    public abstract void convert(HHViewHolder holder, T t) ;
}
