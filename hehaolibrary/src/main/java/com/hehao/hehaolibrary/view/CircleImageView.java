package com.hehao.hehaolibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.hehao.hehaolibrary.R;
import com.hehao.hehaolibrary.util.HHImageUtils;
import com.hehao.hehaolibrary.util.HHLog;

/**
 * @author HeHao
 * @time 2015/11/16 11:46
 * @email 139940512@qq.com
 */
public class CircleImageView extends ImageView {

    /**默认属性*/
    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;

    /**边框的宽度和颜色*/
    private int borderWidth = DEFAULT_BORDER_WIDTH;
    private int borderColor = DEFAULT_BORDER_COLOR;

    /**控件宽度和高度*/
    private int width;
    private int height;

    private BitmapShader bitmapShader;
    private Matrix matrix;
    private Paint paint;
    private Paint ringPaint;


    public CircleImageView(Context context) {
        super(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        HHLog.d(CircleImageView.class, "two");

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, 0, 0);
        borderColor = array.getColor(R.styleable.CircleImageView_border_color, DEFAULT_BORDER_COLOR);
        borderWidth = array.getDimensionPixelSize(R.styleable.CircleImageView_border_width, DEFAULT_BORDER_WIDTH);
        array.recycle();

        matrix = new Matrix();
        paint = new Paint();

        ringPaint = new Paint();
        ringPaint.setAntiAlias(true);
        ringPaint.setStyle(Paint.Style.STROKE);
        ringPaint.setStrokeWidth(borderWidth);
        ringPaint.setColor(borderColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w ;
        height = h ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(getDrawable() == null)
            return;

        setUpShader();

        RectF rect = new RectF(0,0,width,height);
        canvas.drawOval(rect, paint);
        //canvas.drawOval(0, 0, width, height, paint);

        if(borderWidth > 0){
            RectF bodrderRect = new RectF(borderWidth/2, borderWidth/2, width-borderWidth/2, height-borderWidth/2);
            canvas.drawOval(bodrderRect, ringPaint);
            //canvas.drawOval(borderWidth/2, borderWidth/2, width-borderWidth/2, height-borderWidth/2, ringPaint);
        }
    }

    /**初始化BitmapShader*/
    private void setUpShader(){
        Drawable drawable = getDrawable();
        if(drawable == null)
            return ;

        Bitmap bmp = HHImageUtils.drawableToBitmap(drawable);
        bitmapShader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        //x,y上的放缩参数
        float scaleX = width*1.0f/bmp.getWidth();
        float scaleY = height*1.0f/bmp.getHeight();

        matrix.setScale(scaleX, scaleY);
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
    }
}
