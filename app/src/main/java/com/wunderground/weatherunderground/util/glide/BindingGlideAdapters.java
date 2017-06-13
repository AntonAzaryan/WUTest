package com.wunderground.weatherunderground.util.glide;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.content.res.AppCompatResources;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by anton on 19.01.17.
 */

public class BindingGlideAdapters {

    @BindingAdapter("glideUrl")
    public static void loadUrlImage(ImageView view, String imageUrl) {
        Glide.clear(view);
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }

    @BindingAdapter({"glideUrl", "glidePlaceholder"})
    public static void loadUrlImageWithPlaceholder(ImageView view, String imageUrl, int placeholder) {
        final Context context = view.getContext();
        Glide.clear(view);
        Glide.with(context)
                .load(imageUrl)
                .placeholder(AppCompatResources.getDrawable(context, placeholder))
                .into(view);
    }

    @BindingAdapter("glideResId")
    public static void loadResIdImage(ImageView view, int imageResId) {
        final Context context = view.getContext();
        Glide.clear(view);
        Glide.with(context)
                .load(imageResId)
                .dontAnimate()
                .into(view);
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter({"glideUrl", "glideTransform"})
    public static void loadUrlImageWithTransform(ImageView view, String imageUrl, GlideTransformations.GlideTransformationFactory transformFactory) {
        final Context context = view.getContext();
        Glide.clear(view);
        Glide.with(context)
                .load(imageUrl)
                .bitmapTransform(transformFactory.setContext(context).create())
                .into(view);
    }
}
