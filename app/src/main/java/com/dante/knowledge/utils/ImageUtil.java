package com.dante.knowledge.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dante.knowledge.KnowledgeApp;

/**
 * loading img encapsulation.
 */
public class ImageUtil {

    public static void load(Context context, String url, ImageView view) {
        Glide.with(context.getApplicationContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(view);
    }

    public static void load( String url, int animationId, ImageView view) {
        Glide.with(KnowledgeApp.context)
                .load(url)
                .animate(animationId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

}
