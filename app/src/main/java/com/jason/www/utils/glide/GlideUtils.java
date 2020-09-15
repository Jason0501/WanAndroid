package com.jason.www.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.target.Target;
import com.jason.www.R;

import java.io.File;

import androidx.core.content.ContextCompat;

public class GlideUtils {
    private static Context context;

    public static void init(Context mContext) {
        context = mContext;
    }

    private static RequestManager with() {
        return Glide.with(context);
    }

    private static RequestBuilder<Drawable> load(String imgUrl) {
        return with().load(imgUrl);
    }

    private static RequestBuilder<Drawable> load(int resId) {
        return with().load(resId);
    }

    private static RequestBuilder<Bitmap> loadToBitmap(String imgUrl) {
        return with().asBitmap().load(imgUrl);
    }

    private static RequestBuilder<GifDrawable> loadGif(String imgUrl) {
        return with().asGif().load(imgUrl);
    }

    private static RequestBuilder<Drawable> load(File file) {
        return with().load(file);
    }

    private static BaseRequestOptions setConfiguration(RequestBuilder request) {
        return request.diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .error(R.drawable.default_pic)
                .placeholder(R.drawable.default_pic)
                .centerCrop();
    }


    private static BaseRequestOptions setConfiguration(RequestBuilder request, int resId) {
        return request.diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .error(resId)
                .placeholder(resId)
                .centerCrop();
    }

    private static BaseRequestOptions setConfigurationCenterInside(RequestBuilder request, int errorResId) {
        return request.diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .error(errorResId)
                .placeholder(errorResId)
                .centerCrop();
    }


    public static void loadImage(String imgUrl, ImageView imageView) {
        RequestBuilder<Drawable> requestBuilder = load(imgUrl);
        setConfiguration(requestBuilder);
        requestBuilder.into(imageView);
    }

    public static void loadImage(int resId, ImageView imageView) {
        RequestBuilder<Drawable> requestBuilder = load(resId);
        setConfiguration(requestBuilder);
        requestBuilder.into(imageView);
    }

    public static void loadImage(File file, ImageView imageView) {
        RequestBuilder<Drawable> requestBuilder = load(file);
        setConfiguration(requestBuilder);
        requestBuilder.into(imageView);
    }

    /**
     * 圆图
     *
     * @param imgUrl
     * @param imageView
     */
    public static void loadCircularImage(String imgUrl, ImageView imageView) {
        RequestBuilder<Drawable> requestBuilder = load(imgUrl);
        setConfiguration(requestBuilder);
        requestBuilder.transform(new CropCircleTransformation(context))
                .into(imageView);
    }

    /**
     * 加载正方形图片带圆角
     *
     * @param imgUrl
     * @param imageView
     */
    public static void loadRoundImage(String imgUrl, ImageView imageView) {
        RequestBuilder<Drawable> requestBuilder = load(imgUrl);
        setConfiguration(requestBuilder);
        requestBuilder.transform(new RoundedCornersTransformation(context, 5, 0),
                new ColorFilterTransformation(context, ContextCompat.getColor(context, R.color.trans_6)))
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(imageView);

    }
}
