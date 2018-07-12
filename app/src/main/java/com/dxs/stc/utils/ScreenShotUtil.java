package com.dxs.stc.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * created by hl at 2018/7/12
 * com.dxs.stc.utils.ScreenShotUtil
 *
 * @version V1.0 <描述当前版本功能>
 */
public class ScreenShotUtil {

    public static Bitmap Screenshot(Activity activity, Dialog dialog) {
        View mainView = activity.getWindow().getDecorView();
        //也可以用下面的，效果等同于上面的效果
        //mainView= findViewById(android.R.id.content);
        mainView.setDrawingCacheEnabled(true);
        mainView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(mainView.getDrawingCache(), 0, 0, mainView.getWidth(), mainView.getHeight());

        //如果需要同时保存打开的dialog的截图，可以这么做，如果不需要，上面的bitmap就是当前activity的截图了。
        View dialogView = dialog.getWindow().getDecorView();
        int location[] = new int[2];
        mainView.getLocationOnScreen(location);
        int location2[] = new int[2];
        dialogView.getLocationOnScreen(location2);
        dialogView.setDrawingCacheEnabled(true);
        dialogView.buildDrawingCache();
        Bitmap bitmap2 = Bitmap.createBitmap(dialogView.getDrawingCache(), 0, 0, dialogView.getWidth(), dialogView.getHeight());

        Canvas canvas = new Canvas(bitmap);
        //给没有背景的dialog绘制一层半透明的图层
        canvas.drawBitmap(makeSrc(mainView.getWidth(), mainView.getHeight()), 0, 0, new Paint());
        canvas.drawBitmap(bitmap2, location2[0] - location[0], location2[1] - location[1], new Paint());
        mainView.destroyDrawingCache();
        dialogView.destroyDrawingCache();

        return bitmap;
    }


    /**
     * 根据view来生成bitmap图片，可用于截图功能
     */
    public static Bitmap GetViewBitmap(View v) {
        v.clearFocus(); //
        v.setPressed(false); //
        // 能画缓存就返回false
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap;

    }

    /**
     * 截屏
     *
     * @param activity
     * @return
     */
    public static Bitmap CaptureScreen(Activity activity) {

        View mainView = activity.getWindow().getDecorView();
        //也可以用下面的，效果等同于上面的效果
        //mainView= findViewById(android.R.id.content);
        mainView.setDrawingCacheEnabled(true);
        mainView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(mainView.getDrawingCache(), 0, 0, mainView.getWidth(), mainView.getHeight());
        return bitmap;
    }

    /**
     * 保存Bitmap图片为本地文件
     */
    public static void SaveFile(Bitmap bitmap, String filename) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date);
        Loger.debug("Date获取当前日期时间: " + simpleDateFormat.format(date));

        File extDir = Environment.getExternalStorageDirectory();
        File fullFilename = new File(extDir, "/Pictures/" + filename + simpleDateFormat.format(date) + ".jpg");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fullFilename);
            if (fileOutputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // create a bitmap with a rect, used for the "src" image
    static Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(Color.parseColor("#80000000"));
        c.drawRect(0, 0, w, h, p);
        return bm;
    }
}