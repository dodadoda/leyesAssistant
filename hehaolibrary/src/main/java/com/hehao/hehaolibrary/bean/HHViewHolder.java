package com.hehao.hehaolibrary.bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * @author HeHao
 * @time 2015/11/6 17:50
 * @email 139940512@qq.com
 */
public class HHViewHolder {

    private SparseArray<View> mView;
    private int mPosition ;
    private View mConvertView ;

    public HHViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        mPosition = position ;
        mView = new SparseArray<View>() ;
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false) ;

        mConvertView.setTag(this);
    }

    public static HHViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {

        if(convertView == null){
            return new HHViewHolder(context, parent, layoutId, position) ;
        }
        else {
            HHViewHolder holder = (HHViewHolder) convertView.getTag() ;
            holder.mPosition = position ;
            return holder ;
        }
    }

    public <T extends View> T getView(int viewId){
        View view = mView.get(viewId) ;

        if(view == null){
            view = mConvertView.findViewById(viewId) ;
            mView.put(viewId, view);
        }
        return (T) view ;
    }

    public View getConvertView(){
        return mConvertView ;
    }

    public HHViewHolder setText(int viewId, String text){
        TextView tv = getView(viewId) ;
        tv.setText(text);
        return this ;
    }

    public HHViewHolder setImageResource(int viewId, int resId){
        ImageView iv = getView(viewId) ;
        iv.setImageResource(resId);
        return this ;
    }

    public HHViewHolder setImageBitmap(int viewId, Bitmap bitmap){
        ImageView iv = getView(viewId) ;
        iv.setImageBitmap(bitmap);
        return this ;
    }
}
