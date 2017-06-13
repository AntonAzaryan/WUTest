package com.wunderground.weatherunderground.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by Anton Azaryan on 12.06.2017.
 */

public class Utils {

    public static float dp2px(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static String tempIntToString(int temperature) {
        return temperature + "" + (char) 0x00B0;
    }
}
