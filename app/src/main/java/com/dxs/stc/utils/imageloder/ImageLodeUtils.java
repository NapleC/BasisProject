package com.dxs.stc.utils.imageloder;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dxs.stc.R;
import com.dxs.stc.utils.imageloder.transformations.RoundedCornersTransformation;

/**
 * created by hl at 2018/5/14
 * ImageLodeUtils
 * 基于glide 4.7.1
 *
 * @copyright https://juejin.im/entry/5ad5555f51882555867fe935
 * @copyright https://github.com/wasabeef/glide-transformations
 */

public class ImageLodeUtils {


    /**
     * 默认方法加载图片到ImageView
     * ----------------------------------------------------------------------------------------------------
     */
    public static void bindImage(Context mContext, String path, ImageView imageView) {
        Glide.with(mContext)
                .load(path)
                .into(imageView);
    }

    public static void bindImage(Context mContext, int res, ImageView imageView) {
        Glide.with(mContext)
                .load(res)
                .into(imageView);
    }

    public static void bindImage(Fragment fragment, String path, ImageView imageView) {
        Glide.with(fragment)
                .load(path)
                .into(imageView);
    }

    public static void bindImage(Fragment fragment, int res, ImageView imageView) {
        Glide.with(fragment)
                .load(res)
                .into(imageView);
    }

    /**
     * 加载原图
     *
     * @param mContext
     * @param path
     * @param mImageView
     * @param loadingImage
     * @param errorImageView
     */
    public static void loadOriginalImage(Context mContext, String path,
                                         ImageView mImageView, int loadingImage, int errorImageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(loadingImage)
                .error(errorImageView)
                .override(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL);
        Glide.with(mContext).load(path).apply(options).into(mImageView);
    }

    /**
     * 设置加载中以及加载失败图片
     * ----------------------------------------------------------------------------------------------------
     */
    public static void loadingImage(Context mContext, String path, ImageView mImageView) {

        RequestOptions options = new RequestOptions()
                .placeholder(R.color.white)
                .error(R.color.white);
        Glide.with(mContext).load(path).apply(options).into(mImageView);
    }

    /**
     * 加载设定宽高的图片
     *
     * @param mContext
     * @param path
     * @param mImageView
     * @param loadingImage
     * @param errorImageView
     * @param width
     * @param height
     */
    public static void loadingSizeImage(Context mContext, String path, ImageView mImageView,
                                        int loadingImage, int errorImageView, int width, int height) {

        RequestOptions options = new RequestOptions()
                .placeholder(loadingImage)
                .error(errorImageView)
                .override(width, height);
        Glide.with(mContext).load(path).apply(options).into(mImageView);
    }

    /**
     * 加载圆形图片
     *
     * @param mContext
     * @param path
     * @param mImageView
     */
    public static void loadingCircleImage(Context mContext, String path, ImageView mImageView) {

        RequestOptions requestOptions = RequestOptions.circleCropTransform();
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .circleCrop()//设置圆形
//                .placeholder(R.color.white)
//                .error(R.color.white)
//                .diskCacheStrategy(DiskCacheStrategy.ALL);
//        Glide.with(mContext).load(path).apply(options).into(mImageView);
        Glide.with(mContext).load(path).apply(requestOptions).into(mImageView);

    }

    /**
     * 通过Glide 加载 gif
     * ----------------------------------------------------------------------------------------------------
     */
    public static void loadGIF(Context mContext, String url, int loadingImage,
                               int errorImageView, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(loadingImage)
                .error(errorImageView)
                .override(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL);
        Glide.with(mContext).asGif().load(url).apply(options).into(imageView);
    }

    /**
     * 通过Glide 加载 Bitmap
     * ----------------------------------------------------------------------------------------------------
     */
    public static void loadBitmap(Context mContext, @Nullable Bitmap bitmap, int loadingImage,
                                  int errorImageView, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(loadingImage)
                .error(errorImageView)
                .override(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL);
        Glide.with(mContext).asBitmap().load(bitmap).apply(options).into(imageView);
    }

    /**
     * 通过Glide 加载 Drawable
     * ----------------------------------------------------------------------------------------------------
     */
    public static void loadDrawable(Context mContext,
                                    @RawRes @DrawableRes @Nullable Integer resourceId, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.color.white)
                .error(R.color.white)
                .override(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL);
        Glide.with(mContext).asDrawable().load(resourceId).apply(options).into(imageView);
    }


    /**
     * 加载45圆角的图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadRoundImage(Context context, String url, ImageView imageView) {
        RequestOptions options = RequestOptions
                .bitmapTransform(
                        new RoundedCornersTransformation(
                                45, 0, RoundedCornersTransformation.CornerType.ALL))
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);

    }

    /**
     * 加载圆角图片自定义圆角
     *
     * @param context
     * @param url
     * @param imageView
     * @param radius
     */
    public static void loadRoundRadiusImage(Context context, String url, ImageView imageView, int radius) {
        RequestOptions options = RequestOptions
                .bitmapTransform(
                        new RoundedCornersTransformation(
                                radius, 0, RoundedCornersTransformation.CornerType.ALL))
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);

    }

    /**
     * 加载圆角图片-指定任意部分圆角（图片上、下、左、右四个角度任意定义）
     *
     * @param context
     * @param url
     * @param imageView
     * @param type
     */
    public static void loadCustRoundCircleImage(Context context, String url, ImageView imageView,
                                                RoundedCornersTransformation.CornerType type) {

        RequestOptions options = RequestOptions
                .bitmapTransform(new RoundedCornersTransformation(45, 0, type))
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context).load(url).apply(options).into(imageView);
    }


    public static int dip2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 清理缓存
     * ----------------------------------------------------------------------------------------------------
     */
    public static void CleanImageCache(Context mContext) {
        Glide.get(mContext).clearDiskCache();
    }

}
