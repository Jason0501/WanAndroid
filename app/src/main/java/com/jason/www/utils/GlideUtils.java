package com.jason.www.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.jason.www.R;

import java.io.File;

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

    private static BaseRequestOptions setAvatarConfiguration(RequestBuilder request) {
        BaseRequestOptions options = setConfiguration(request);
        return options.error(R.drawable.default_avatar);
    }

    public static void pauseLoad() {
        with().pauseRequests();
    }

    public static void resumeLoad() {
        with().resumeRequests();
    }

    public static void loadAvatar(ImageView imageView, String imgUrl) {
        RequestBuilder<Drawable> requestBuilder = load(imgUrl);
        setAvatarConfiguration(requestBuilder);
        requestBuilder.into(imageView);
    }

    public static void loadImage(ImageView imageView, String imgUrl) {
        RequestBuilder<Drawable> requestBuilder = load(imgUrl);
        setConfiguration(requestBuilder);
        requestBuilder.into(imageView);
    }

    public static void loadImage(ImageView imageView, int resId) {
        RequestBuilder<Drawable> requestBuilder = load(resId);
        setConfiguration(requestBuilder);
        requestBuilder.into(imageView);
    }

    public static void loadImage(ImageView imageView, File file) {
        RequestBuilder<Drawable> requestBuilder = load(file);
        setConfiguration(requestBuilder);
        requestBuilder.into(imageView);
    }
}
