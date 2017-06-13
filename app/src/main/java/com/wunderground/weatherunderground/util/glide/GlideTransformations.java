package com.wunderground.weatherunderground.util.glide;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.Transformation;
import com.wunderground.weatherunderground.util.Utils;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by anton on 13.02.17.
 */

public class GlideTransformations {

    /**
     * Some of Transformations uses Context in there constructors.
     * First use setContext() on GlideTransformationFactory, then create().
     */
    public interface GlideTransformationFactory {
        GlideTransformationFactory setContext(Context context);

        Transformation<Bitmap> create();
    }

    /**
     * Base factory class to wrap {@link GlideTransformationFactory} setContext(Context) method.
     */
    static abstract class BaseGlideTransformationFactory implements GlideTransformationFactory {
        Context context;

        @Override
        public GlideTransformationFactory setContext(Context context) {
            this.context = context;
            return this;
        }
    }

    public static GlideTransformations.GlideTransformationFactory circle() {
        return new BaseGlideTransformationFactory() {
            @Override
            public Transformation<Bitmap> create() {
                return new CropCircleTransformation(context);
            }
        };
    }

    public static GlideTransformations.GlideTransformationFactory roundedCornersDp(int radiusDp) {
        return new BaseGlideTransformationFactory() {
            @Override
            public Transformation<Bitmap> create() {
                return new RoundedCornersTransformation(context, (int) Utils.dp2px(radiusDp, context), 0);
            }
        };
    }
}
