package com.wunderground.weatherunderground.ui.widget.decoration;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;


/**
 * Created by anton on 26.04.17.
 */

public abstract class ItemDecorationFactory {

    public abstract RecyclerView.ItemDecoration create(Context context);

    public static ItemDecorationFactory defaultDivider() {
        return new ItemDecorationFactory() {
            @Override
            public RecyclerView.ItemDecoration create(Context context) {
                return new DividerItemDecoration(context);
            }
        };
    }

    public static ItemDecorationFactory defaultDivider(int... excludePositions) {
        return new ItemDecorationFactory() {
            @Override
            public RecyclerView.ItemDecoration create(Context context) {
                DividerItemDecoration decoration = new DividerItemDecoration(context);
                decoration.setExcludePositions(excludePositions);
                return decoration;
            }
        };
    }

    public static ItemDecorationFactory defaultDividerExcludeLast() {
        return new ItemDecorationFactory() {
            @Override
            public RecyclerView.ItemDecoration create(Context context) {
                DividerItemDecoration decoration = new DividerItemDecoration(context);
                decoration.setExcludePositions(DividerItemDecoration.EXCLUDE_POSITION_LAST);
                return decoration;
            }
        };
    }

    public static ItemDecorationFactory defaultHorizontalDividerExcludeLast() {
        return new ItemDecorationFactory() {
            @Override
            public RecyclerView.ItemDecoration create(Context context) {
                DividerItemDecoration decoration = new DividerItemDecoration(context, LinearLayout.HORIZONTAL);
                decoration.setExcludePositions(DividerItemDecoration.EXCLUDE_POSITION_LAST);
                return decoration;
            }
        };
    }
}
