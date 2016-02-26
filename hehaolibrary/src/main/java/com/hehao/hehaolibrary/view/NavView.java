package com.hehao.hehaolibrary.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hehao.hehaolibrary.R;
import com.hehao.hehaolibrary.util.HHUtils;

/**
 * @author HeHao
 * @time 2015/11/10 15:48
 * @email 139940512@qq.com
 */
public class NavView extends LinearLayout implements Checkable{

    /**图片*/
    private ImageView img;
    /**文字*/
    private TextView text;
    /**是否选中*/
    private boolean isChecked;

    public NavView(Context context) {
        this(context, null);
    }

    public NavView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NavView, 0, 0);
        if(a != null)
            init(a);
    }

    /**
     * 初始化
     * @param array
     */
    private void init(TypedArray array) {
        //setPadding(5, 5, 5, 5);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        Drawable draw = null;
        CharSequence key = "KEY";
        ColorStateList cl = getResources().getColorStateList(R.color.txt_black);

        int size = array.getIndexCount();
        for(int i=0; i<size; i++){
            int attr = array.getIndex(i);
            if(attr == R.styleable.NavView_navSrc){
                draw = array.getDrawable(attr);
            }
            else if(attr == R.styleable.NavView_navText){
                key = array.getText(attr);
            }
            else if(attr == R.styleable.NavView_navTextColor){
                cl = array.getColorStateList(attr);
            }
        }
        /*img = new ImageView(getContext());
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        img.setImageDrawable(draw);*/

        img = new ImageView(getContext());
        LayoutParams lp = new LayoutParams(HHUtils.dip2px(getContext(), 40),
                HHUtils.dip2px(getContext(), 40));
        img.setImageDrawable(draw);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(img, lp);

        text = new TextView(getContext());
        LayoutParams txtlp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        text.setText(key);
        text.setGravity(Gravity.CENTER);
        text.setTextColor(cl);
        addView(text);

        array.recycle();
    }

    /**
     * 设置选中状态
     * @param checked   true为选中,false为没选中
     */
    public void setChecked(boolean checked){
        if(isChecked != checked){
            isChecked = checked;
            setSelected(isChecked);
        }
    }

    /**
     * 判断按钮是否选中
     * @return  true为选中
     */
    public boolean isChecked(){
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }
}
