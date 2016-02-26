package com.hehao.hehaolibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.hehao.hehaolibrary.R;
import com.hehao.hehaolibrary.util.HHLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 罗盘控件
 * @author HeHao
 * @time 2015/11/21 23:33
 * @email 139940512@qq.com
 */
public class CompassView extends View {
    /**上下文参数*/
    private Context context;
    /**控件宽高*/
    private int viewHeight, viewWidth;
    /**定义画笔*/
    private Paint certenPaint ;
    /**组件列表*/
    private List<CompassItem> items;
    /**touch事件的x,y*/
    private int touchX, touchY;

    private static final int NORMAL = 1;
    private static final int SHOWTEXT = 2;
    private static final int MOVE = 3;
    /**
     * 控件状态
     * 1、正常状态
     * 2、选中了Item状态
     * 3、拖动Item状态
     */
    private int statu = NORMAL;

    /**被选中的控件*/
    private CompassItem chooseItem;

    public CompassView(Context context) {
        super(context, null);
    }

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }



    /**初始化控件*/
    private void init(){
        items = new ArrayList<>();

        certenPaint = new Paint();
        certenPaint.setAntiAlias(true);
        certenPaint.setColor(getResources().getColor(R.color.green));
        certenPaint.setAlpha(100);
    }

    /**向控件中添加组件*/
    public void addItem(CompassItem item){
        if(items != null)
            items.add(item);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCenter(canvas);

        if(statu == SHOWTEXT){
            drawText(canvas);
        }

        if(items != null){
            for(CompassItem item : items){
                item.setCenter( viewWidth / 2, viewHeight / 2);
                item.drawSelf(canvas);
            }
        }

        postInvalidate();
    }

    private void drawText(Canvas canvas) {
        if(chooseItem == null){
            return;
        }
        String text = chooseItem.getText();

        TextPaint textPaint = new TextPaint();
        textPaint.setTextAlign(TextPaint.Align.CENTER);
        textPaint.setTextSize(40);
        textPaint.setFakeBoldText(true);
        textPaint.setColor(getResources().getColor(R.color.green));
        canvas.drawText(text, viewWidth/2, viewHeight/2-100, textPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        viewWidth = w;
    }

    /**绘制中心圆*/
    private void drawCenter(Canvas canvas){

        //圆半径为宽高较小的一边的1/12
        canvas.drawCircle(viewWidth / 2, viewHeight / 2, Math.min(viewWidth, viewHeight) / 12, certenPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                chooseItem = whoChoose(event);
                if(chooseItem != null){
                    chooseItem.changeStatu(CompassItem.CHOOSE);
                    chooseItem.setTouchX((int) event.getX());
                    chooseItem.setTouchY((int)event.getY());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(chooseItem != null){
                    chooseItem.setTouchX((int) event.getX());
                    chooseItem.setTouchY((int)event.getY());
                    if(isInCenter(event)){
                        certenPaint.setAlpha(255);
                        statu = SHOWTEXT;
                    }else {
                        certenPaint.setAlpha(100);
                        statu = NORMAL;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(chooseItem != null){
                    if(isInCenter(event)){
                        Toast.makeText(context, chooseItem.getId()+"", Toast.LENGTH_SHORT).show();
                    }
                    chooseItem.changeStatu(CompassItem.NORMAL);
                    chooseItem = null;
                    certenPaint.setAlpha(100);
                    statu = NORMAL;
                }
                break;
        }
        return true;
    }

    /**是否将控件拖动到了中心位置*/
    private boolean isInCenter(MotionEvent event){
        int certenX = viewWidth/2;
        int certenY = viewHeight/2;
        int certenRadius = Math.min(viewWidth, viewHeight) / 12;
        int distance = (int)Math.sqrt((event.getX() - certenX)*(event.getX()-certenX)+(event.getY()-certenY)*(event.getY()-certenY));
        if(distance < certenRadius)
            return true;
        return false;
    }

    private CompassItem whoChoose(MotionEvent event) {
        CompassItem result;
        if(items != null && items.size() > 0){
            List<CompassItem> tmpItem = new ArrayList<>();
            //找出被选中的控件
            for(CompassItem item: items){
                int distance = item.isInside(event);
                if(distance != -1){
                    tmpItem.add(item);
                }
            }

            //找出点击位置距离圆心最近的控件
            if(tmpItem.size()>0) {
                result = tmpItem.get(0);
                for (CompassItem item : tmpItem) {
                    if (result.isInside(event) > item.isInside(event)) {
                        result = item;
                    }
                }
                return result;
            }
        }

        return null;
    }
}
