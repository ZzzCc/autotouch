package com.hdf.autotouch.util;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.hdf.autotouch.R;

import java.io.File;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ImageLoaderHelper {

    /**
     * 加载网络图片
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .preload();// 预加载
        Glide.with(context)
                .load(url)
                .apply(getOptions())
                .into(imageView);
    }

    /**
     * 加载圆角图片
     */
    public static void loadCircleImage(Context context, String url, ImageView imageView) {

        RequestOptions roundOptions = new RequestOptions()
                .transform(new RoundedCorners(10));
        Glide.with(context)
                .load(url)
                .apply(roundOptions)
                .into(imageView);
    }

    public static void loadImage1(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .preload();// 预加载
        Glide.with(context)
                .load(url)
                .apply(getOptions1())
                .into(imageView);
    }

    /**
     * 加载本地图片
     */
    public static void loadImage(Context context, File file, ImageView imageView) {
        Glide.with(context)
                .load(file)
                .apply(getNoCacheOptions())
                .into(imageView);
    }

    /**
     * 加载应用资源
     */
    public static void loadImage(Context context, int resource, ImageView imageView) {
        Glide.with(context)
                .load(resource)
                .apply(getNoCacheOptions())
                .into(imageView);
    }

    /**
     * 加载二进制流
     */
    public static void loadImage(Context context, byte[] image, ImageView imageView) {
        Glide.with(context)
                .load(image)
                .apply(getNoCacheOptions())
                .into(imageView);
    }

    /**
     * 加载Uri图片
     */
    public static void loadImage(Context context, Uri uri, ImageView imageView) {
        Glide.with(context)
                .load(uri)
                .apply(getNoCacheOptions())
                .into(imageView);
    }

    public static void loadCirclImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(getCircleOptions())
                .into(imageView);
    }

    private static RequestOptions getOptions() {
        return new RequestOptions()
                .placeholder(ContextCompat.getDrawable(Utils.getApp(), R.drawable.bg_solid_block_4))
                .error(ContextCompat.getDrawable(Utils.getApp(), R.drawable.bg_solid_block_4));
    }

    private static RequestOptions getOptions1() {
        return new RequestOptions()
                .placeholder(new ColorDrawable(ColorUtils.getColor(R.color.medium_gray)))
                .error(new ColorDrawable(ColorUtils.getColor(R.color.medium_gray)));
    }

    private static RequestOptions getNoCacheOptions() {
        return getOptions()
                .placeholder(ContextCompat.getDrawable(Utils.getApp(), R.drawable.bg_solid_block_4))
                .error(ContextCompat.getDrawable(Utils.getApp(), R.drawable.bg_solid_block_4))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
    }

    private static RequestOptions getCircleOptions() {
        return new RequestOptions()
                .placeholder(ContextCompat.getDrawable(Utils.getApp(), R.drawable.bg_solid_block_4))
                .error(ContextCompat.getDrawable(Utils.getApp(), R.drawable.bg_solid_block_4))
                .transforms(new CircleCrop());
    }

    private static RequestOptions getRoundedCornersOptions() {
        return getOptions().transforms(new RoundedCornersTransformation(8, 0));
    }
}