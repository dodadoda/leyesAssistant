package com.hehao.hehaolibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * @author HeHao
 * @time 2015/11/6 16:44
 * @email 139940512@qq.com
 */
public class TimeButton extends Button implements Runnable{
    private static final int INTERNAL = 60;
    private int time;
    /**控件状态*/
    private int state;

    private static final int NORMAL = 0;
    private static final int RUNNING = 1;

    public TimeButton(Context context) {
        super(context);
    }

    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        state = NORMAL;
    }

    /**
     * 设置文字
     * @param txt   被设置的文字
     */
    public void setButtonTxt(String txt){
        if(state == NORMAL)
            this.setText(txt);
    }

    /**
     * 开始计时
     */
    public void start(){
        time = INTERNAL;
        if(time < 0)
            return;
        state = RUNNING;
        this.setClickable(false);
        run();
    }

    /**
     * 让按钮处于准备状态
     */
    public void praper(){
        this.setText("发送中...");
        this.setClickable(false);
    }

    /**
     * 让按钮处于终止状态
     */
    public void end(){
        this.setText("重新发送");
        this.setClickable(true);
        state = NORMAL;
        time = INTERNAL;
    }

    @Override
    public void run() {
        time --;
        if(time <= 0) {
            end();
            return;
        }
        updateTime();
        postDelayed(this, 1000);
    }

    private void updateTime(){
        this.setText(time+"秒后重新发送");
    }
}
