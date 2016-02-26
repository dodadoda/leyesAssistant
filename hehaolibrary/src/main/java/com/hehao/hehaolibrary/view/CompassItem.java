package com.hehao.hehaolibrary.view;

/**
 * @author HeHao
 * @time 2015/11/22 22:54
 * @email 139940512@qq.com
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

/**悬浮组件*/
public class CompassItem{
    private int id;             //控件ID
    private int itemRadius;     //选项半径
    private int angle;          //选项角度
    private int aspect;         //旋转方向      0为逆时针，1为顺时针
    private int speed;          //旋转速度      单位角度
    private long startTime;     //控件开始运动时间
    private int alpha;          //控件透明度
    private int color;          //控件颜色
    private int centerRadius;   //距离中心位置半径

    private int centerX, centerY;   //旋转中心

    private int touchX, touchY; //触摸事件坐标

    private String text = "";

    private Paint paint;        //画笔

    public static final int NORMAL = 1;     //正常状态
    public static final int CHOOSE = 2;     //选中状态
    private int statu = NORMAL;

    public static final int ASPECT_CLOCKWISE = 0;
    public static final int ASPECT_ANTICLOCKWISE = 1;

    private int cont = 0;

    /**
     * 构造函数
     * @param angle         初始角度(度)
     * @param speed         速度(度/秒)
     * @param startTime     初始时间
     * @param alpha         透明度
     * @param aspect        旋转方向
     * @param color         颜色
     * @param itemRadius    半径
     */
    public CompassItem(int id, int angle, int speed, long startTime, int alpha, int aspect, int color, int itemRadius, int centerRadius){
        this.id = id;
        this.itemRadius = itemRadius;
        this.angle = angle;
        this.speed = speed;
        this.aspect = aspect;
        this.startTime = startTime;
        this.alpha = alpha;
        this.color = color;
        this.centerRadius = centerRadius;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setAlpha(alpha);
    }

    /**获取x坐标*/
    public int getX(long currentTime){
        float deltaTime = ((float)(currentTime-startTime))/1000;
        float x = (float)Math.cos((deltaTime * speed + angle) * Math.PI / 180)*centerRadius;
        return (int)x;
    }

    /**获取y坐标*/
    public int getY(long currentTime){
        float deltaTime = ((float)(currentTime-startTime))/1000;
        float y = (float)Math.sin((deltaTime * speed + angle) * Math.PI / 180)*centerRadius;
        if(aspect == ASPECT_ANTICLOCKWISE)
            return (int)(-1*y);
        else
            return (int)y;
    }

    /**绘制自己*/
    public void drawSelf(Canvas canvas) {
        Paint txtPaint = new Paint();
        txtPaint.setColor(color);
        txtPaint.setTextSize(30);
        txtPaint.setTextAlign(Paint.Align.CENTER);

        if(statu == NORMAL) {

            int x = getX(System.currentTimeMillis()) + centerX;
            int y = getY(System.currentTimeMillis()) + centerY;
            paint.setAlpha(alpha);
            canvas.drawCircle(x, y, itemRadius, paint);


            canvas.drawText(text, x, y+10 ,txtPaint);
        }else if(statu == CHOOSE){
            paint.setAlpha(200);
            canvas.drawCircle(touchX, touchY, itemRadius, paint);
            canvas.drawText(text, touchX, touchY,txtPaint);
        }
    }

    /**
     * 是否在控件范围内
     * @param event 点击事件
     * @return  距离圆心的距离
     */
    public int isInside(MotionEvent event){
        int x = getX(System.currentTimeMillis())+centerX;
        int y = getY(System.currentTimeMillis())+centerY;
        touchX = (int)event.getX();
        touchY = (int)event.getY();
        int distance = (int)Math.sqrt((touchX-x)*(touchX-x)+(touchY-y)*(touchY-y));
        if(distance < itemRadius){
            return distance;
        }
        return -1;
    }

    /**传入触摸事件的X坐标*/
    public void setTouchX(int touchX){
        this.touchX = touchX;
    }

    /**传入触摸时间的Y坐标*/
    public void setTouchY(int touchY){
        this.touchY = touchY;
    }

    /**改变控件状态*/
    public void changeStatu(int statu){
        this.statu = statu;
    }

    /**设置旋转中心*/
    public void setCenter(int centerX, int centerY){
        this.centerX = centerX;
        this.centerY = centerY;
    }

    /**获取控件ID*/
    public int getId(){
        return id;
    }

    /**设置文字*/
    public void setText(String text){
        this.text = text;
    }

    /**获取文字*/
    public String getText(){
        return text;
    }

}
