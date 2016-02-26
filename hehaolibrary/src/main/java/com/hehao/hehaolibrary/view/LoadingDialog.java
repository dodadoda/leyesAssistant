package com.hehao.hehaolibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.hehao.hehaolibrary.R;

/**
 * 页面加载完成前的dialog
 * @author HeHao
 * @time 2015/11/4 17:12
 * @email 139940512@qq.com
 */
public class LoadingDialog {
    private static LoadingDialog instance = null;
    private Dialog mDialog;

    private ImageView mLabel;
    private Context mContext;

    private LoadingDialog(Context context){
        mContext = context;
    }

    /**
     * 单例模式 获取一个加载对话框
     * @param context
     * @return
     */
    public static LoadingDialog getInstance(Context context){
        if(instance==null)
            instance = new LoadingDialog(context);
        return instance;
    }

    /**
     * 显示加载对话框
     */
    public void showLoadingDialog(){
        mDialog = new Dialog(mContext, R.style.Theme_LoadingDialog);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.loading_dialog, null) ;
        mDialog.setContentView(view);

        mDialog.show();
    }

    /**
     * 关闭显示的LoadingDialog
     */
    public void dismissLoadingDialog(){
        if(mDialog != null)
            mDialog.dismiss();
        mDialog = null;
        return;
    }
}
