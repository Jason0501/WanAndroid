package com.jason.www.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.jason.www.http.response.HomeBanner;
import com.jason.www.utils.glide.GlideUtils;
import com.youth.banner.adapter.BannerAdapter;

import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author：Jason
 * @date：2020/9/15 15:24
 * @email：1129847330@qq.com
 * @description:
 */
public class HomeBannerAdapter extends BannerAdapter<HomeBanner, HomeBannerAdapter.BannerViewHolder> {

    public HomeBannerAdapter() {
        super(Collections.emptyList());
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, HomeBanner data, int position, int size) {
//        Glide.with(holder.itemView)
//                .load(data.getImagePath())
//                .thumbnail(Glide.with(holder.itemView).load(R.drawable.default_pic))
//                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
//                .into(holder.imageView);
        GlideUtils.loadImage(data.getImagePath(), holder.imageView);
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }
}