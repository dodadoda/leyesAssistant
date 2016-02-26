package com.hehao.hehaolibrary.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * @author HeHao
 * @time 2015/11/16 12:07
 * @email 139940512@qq.com
 */
public class HHImageUtils {
    /**
     * 将drawable转化为bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if(drawable instanceof BitmapDrawable)
            return ((BitmapDrawable) drawable).getBitmap();

        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap) ;
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}
