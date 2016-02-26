package com.hehao.hehaolibrary.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hehao.hehaolibrary.R;
import com.hehao.hehaolibrary.util.HHUtils;

/**
 * @author HeHao
 * @time 2015/11/4 11:39
 * @email 139940512@qq.com
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener{
    private BaseApplication app;
    protected Context context;

    private TextView tvTitle;
    private RelativeLayout rlHead, rlMain;
    /**如过使用统一布局，这是布局内容（除去头部）*/
    private View containView;
    /**共同头部*/
    private int commonTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initView();
        initData();
    }

    /**初始化函数*/
    private void init(){
        app = (BaseApplication)getApplication();
        context = this;
        //将activity加入activity栈
        app.addActivity(this);
    }

    /**
     * 开始一个新的activity并结束当前activity
     * @param cls   类<泛型> 跳转的activity
     * @see #startActExit(Intent, Class)
     */
    protected void startActExit(Class<?> cls) {
        startActExit(null, cls);
    }

    /**
     * 开始一个新的activity并结束当前activity
     * @param intent    intent
     * @param cls   类<泛型> 跳转的activity
     */
    protected void startActExit(Intent intent, Class<?> cls) {
        app.clear();
        startAct(intent, cls);
    }

    /**
     * 启动一个新的activity
     * @param cls   类
     * @see #startAct(Intent, Class)
     */
    protected void startAct(Class<?> cls) {
        startAct(null, cls);
    }

    /**
     * 启动一个新的activity
     * @param intent    intent
     * @param cls   类
     */
    protected void startAct(Intent intent, Class<?> cls) {
        if(intent==null){
            intent = new Intent();
        }
        intent.setClass(this, cls);
        startActivity(intent);
    }

    /**
     * 使用统一头部布局
     * @param layout    布局内容（不包含头部）
     */
    protected void setLayoutWithCommonHeader(int layout) {
        if(layout < 1 )
            return ;
        setContentView(R.layout.base);
        rlHead = getView(R.id.rlHead);
        rlMain = getView(R.id.rlMain);
        tvTitle = getView(R.id.head_title);

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

            int titleSize = a.getDimensionPixelSize(R.styleable.CommonTheme_titlebarTextSize,0);
            if(titleSize != 0){
                tvTitle.setTextSize(titleSize);
            }
        }

        containView = getLayoutInflater().inflate(layout, null) ;
        if(containView != null)
            resetLayout(containView) ;
    }

    /**
     * 获取App类
     * @return
     */
    public BaseApplication getApp(){
        return app;
    }

    /**
     * 获取组件
     * @param id    组件的id
     * @param <T>   组件类型
     * @return
     */
    public <T extends View> T getView(int id){
        View v = findViewById(id);
        return (T)v;
    }

    /**
     * 获取组件
     * @param view  组件所在父控件
     * @param id    组件id
     * @param <T>   泛型
     * @return
     */
    public <T extends View> T getView(View view, int id){
        View v = view.findViewById(id);
        return (T)v;
    }

    /**
     * 获取控件并给控件绑定点击事件
     * 绑定后在子类中复写onViewClick(View v)方法，并在其中实现
     * @param id    控件ID
     * @param <T>
     * @return
     */
    public <T extends View> T getViewWithClick(int id){
        View v = findViewById(id);
        v.setOnClickListener(this);
        return (T)v;
    }

    /**
     * 将布局写入主体部分
     * @param view  待写入的布局
     */
    private void resetLayout(View view){
        rlMain.removeAllViews();
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
                        ,RelativeLayout.LayoutParams.MATCH_PARENT);
        rlMain.addView(view, params);
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
        setHeadTitle(title, 0);
    }

    /**
     * 设置页面头部标题
     * @param title 标题字符资源
     * @param color 标题颜色资源
     */
    protected void setHeadTitle(int title, int color){
        if(title > 0)
            ((TextView)getView(R.id.head_title)).setText(title);
        if(color > 0)
            ((TextView)getView(R.id.head_title)).setTextColor(color);
    }

    /**
     * 设置titlebar左边按钮图片
     * @param leftIcon  图片资源
     */
    protected void setHeadLeft(int leftIcon){
        if(leftIcon > 0){
            View v = getLayoutInflater().inflate(R.layout.in_head_left, rlHead);
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
            View v = getLayoutInflater().inflate(R.layout.in_head_right, rlHead);
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
            View v = getLayoutInflater().inflate(R.layout.in_head_tright, rlHead);
            TextView txt = getView(v, R.id.head_tright);
            txt.setOnClickListener(this);
            txt.setText(rightTxt);
        }
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

    /**
     * 触发头部之外的点击事件
     * @param v
     */
    protected abstract void onViewClick(View v);

    /**左边按钮点击事件*/
    protected void onLeftClick(){}

    /**右边按钮点击事件*/
    protected void onRightClick(){}

    /**初始化布局*/
    protected abstract void initView();

    /**初始化数据*/
    protected abstract void initData();
}
