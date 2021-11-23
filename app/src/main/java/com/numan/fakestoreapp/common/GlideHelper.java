package com.numan.fakestoreapp.common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * This class saves boilerplate code for using glide.
 * Just use the static method to load images using glide instead of creating
 * glide builder request each time.
 */
public class GlideHelper {

    /**
     * Load image using glide.
     *
     * @param context   context
     * @param url       url to load
     * @param tag       tag for error logging
     * @param imageView imageView to load image in.
     */
    public static void loadImageWithGlide(Context context, String url, String tag, ImageView imageView) {

        Glide.with(context)
                .load(url)
                .dontAnimate()
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e(tag, "onLoadFailed: " + e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.IMMEDIATE)
                .into(imageView);

    }

}
