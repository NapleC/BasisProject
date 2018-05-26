package com.dxs.stc.widget;

import android.content.Context;
import android.widget.ImageView;

import com.dxs.stc.utils.imageloder.ImageLodeUtils;
import com.youth.banner.loader.ImageLoader;

/**
 * created by hl at 2018/5/16
 * com.dxs.stc.widget.GlideImageLoad
 *
 * @version V1.0 为banner做的图片加载
 */
public class GlideImageLoad extends ImageLoader {
    /**
     注意：
     1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
     2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
     传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
     切记不要胡乱强转！
     */

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        ImageLodeUtils.loadingImage(context,(String) path,imageView);
    }
}
