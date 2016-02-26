package com.hehao.hehaolibrary.common;

import android.app.Fragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hehao.hehaolibrary.R;
import com.hehao.hehaolibrary.util.HHUtils;

/**
 * @author HeHao
 * @time 2015/11/9 15:41
 * @email 139940512@qq.com
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{
    protected BaseApplication app;
    protected Context context;

    private RelativeLayout rlHead, rlMain;
    private TextView tvTitle;

    private View contentView;

    /**是否初始化*/
    private boolean isInit;

    /**共同头部*/
    private int commonTheme;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        app = (BaseApplication)getActivity().getApplication();

        View v = inflater.inflate(R.layout.base, container, false);
        rlHead = getView(v, R.id.rlHead);
        rlMain = getView(v, R.id.rlMain);
        tvTitle = getView(v, R.id.head_title);

        isInit = false;

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!isInit){
            isInit = true;
            initView();
            initData();
        }
    }

    /**
     * 头部基调设置
     * @param commonTheme
     */
    protected void initTheme(int commonTheme){
        this.commonTheme = commonTheme;
    }

    /**
     * 设置页面头部标题
     * @param title 头部字符资源
     * @see #setHeadTitle(int, int)
     */
    protected void setHeadTitle(int title){
        this.setHeadTitle(title, 0);
    }

    /**
     * 设置页面头部标题
     * @param title
     */
    protected void setHeadTitle(CharSequence title) {
        tvTitle.setText(title);
    }
    /**
     * 设置页面头部标题
     * @param title 标题字符资源
     * @param color 标题颜色资源
     */
    protected void setHeadTitle(int title, int color){
        if(title > 0)
            tvTitle.setText(title);
        if(color > 0)
            tvTitle.setTextColor(color);
    }

    /**
     * 设置titlebar左边按钮图片
     * @param leftIcon  图片资源
     */
    protected void setHeadLeft(int leftIcon){
        if(leftIcon > 0){
            View v = getActivity().getLayoutInflater().inflate(R.layout.in_head_left, rlHead);
            ImageView img = getView(v,R.id.head_left);
            img.setImageResource(leftIcon);
            img.setOnClickListener(this);
        }
    }

    /**
     * 设置titlebar右边按钮图片
     * @param rightIcon 图片资源
     */
    protected void setHeadRight(int rightIcon){
        if(rightIcon > 0){
            View v = getActivity().getLayoutInflater().inflate(R.layout.in_head_right, rlHead);
            ImageView img = getView(v, R.id.head_right) ;
            img.setImageResource(rightIcon);
            img.setOnClickListener(this);
        }
    }

    /**
     * 设置titlebar右边文字
     * @param rightTxt  右方文字资源
     */
    protected void setHeadRightTxt(int rightTxt){
        if(rightTxt > 0){
            View v = getActivity().getLayoutInflater().inflate(R.layout.in_head_tright, rlHead);
            TextView txt = getView(v, R.id.head_tright);
            txt.setOnClickListener(this);
            txt.setText(rightTxt);
        }
    }

    /**
     * 使用统一头部布局
     * @param layout    除头部之外的内容资源
     */
    protected void setLayoutWithCommonHeader(int layout) {
        if(commonTheme > 0){
            TypedArray a = getResources().obtainTypedArray(commonTheme);
            Drawable drawable = a.getDrawable(R.styleable.CommonTheme_titlebarBackground);
            if(drawable != null) {
                rlHead.setBackground(drawable);
            }

            int titleColor = a.getColor(R.styleable.CommonTheme_titlebarTextColor, 0);
            if(titleColor != 0){
                tvTitle.setTextColor(titleColor);
            }

            int titleSize = a.getDimensionPixelSize(R.styleable.CommonTheme_titlebarTextSize, 0);
            if(titleSize != 0){
                tvTitle.setTextSize(titleSize);
            }
        }
        contentView = getActivity().getLayoutInflater().inflate(layout, null);
        if(contentView != null)
            resetLayout(contentView);
    }

    /**
     * 初始化布局方法
     * @param contentView   被初始化的资源
     */
    private void resetLayout(View contentView){
        rlMain.removeAllViews();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        rlMain.addView(contentView, params);
    }

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 获取控件
     * @param id    获取id为给定id的控件
     * @param <T>   控件类型<泛型>
     * @return  获取到的控件
     */
    public <T extends View> T getView(int id) {
        return this.getView(getView(), id);
    }

    /**
     * 获取控件
     * @param v 指定的view中
     * @param id    获取id为给定id的控件
     * @param <T>   控件类型<泛型>
     * @return  获取到的控件
     */
    public <T extends View> T getView(View v, int id){
        View view = v.findViewById(id);
        return (T)view;
    }

    /**
     * 获取控件并为之绑定点击事件
     * @param id    控件id
     * @param <T>   <泛型>
     * @return
     */
    public <T extends View> T getViewWithClick(int id) {
        return this.getViewWithClick(getView(), id);
    }

    /**
     * 获取控件并为之绑定点击事件
     * @param v
     * @param id    控件id
     * @param <T>   <泛型>
     * @return
     */
    public <T extends View> T getViewWithClick(View v, int id) {
        View view = v.findViewById(id);
        view.setOnClickListener(this);
        return (T)view;
    }

    @Override
    public void onClick(View v) {
        if(HHUtils.isFastClick())
            return;
        if(v.getId()==R.id.head_left)
            onLeftClick();
        else if(v.getId()==R.id.head_right)
            onRightClick();
        else if(v.getId()==R.id.head_tright)
            onRightClick();
        else
            onViewClick(v);
    }

    protected abstract void onViewClick(View v);

    protected abstract void onLeftClick();

    protected abstract void onRightClick();
    /**当fragment被隐藏后再次展示,需要时手动调用*/
    protected abstract void onStartData();
}
