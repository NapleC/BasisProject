package com.dxs.stc.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public static void Screenshot(View v, Activity activity) {

        View tempView = v;
        Activity tempActivity = activity;
        String fName = activity.getFilesDir().getPath() + "/screenshot.png";
        new Thread(new Runnable() {
            @Override
            public void run() {
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                tempActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        View view = tempView.getRootView();
                        view.setDrawingCacheEnabled(true);
                        view.buildDrawingCache();
                        Bitmap bitmap = view.getDrawingCache();
                        Loger.debug("test");
                        if (bitmap != null) {
                            Loger.debug("bitmap got!");
                            try {
                                FileOutputStream out = new FileOutputStream(fName);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

                                Loger.debug("test fileOutputStream");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }).start();
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
        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);
        Bitmap bmp = activity.getWindow().getDecorView().getDrawingCache();
        return bmp;
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
        File fullFilename = new File(extDir, "/Pictures/"+filename + simpleDateFormat.format(date) + ".jpg");
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
}
