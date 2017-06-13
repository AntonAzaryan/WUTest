package com.wunderground.weatherunderground.ui.widget.decoration;

/**
 * Created by anton on 01.02.17.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.wunderground.weatherunderground.R;

/**
 * DividerItemDecoration is a {@link RecyclerView.ItemDecoration} that can be used as a divider
 * between items of a {@link LinearLayoutManager}. It supports both {@link #HORIZONTAL} and
 * {@link #VERTICAL} orientations.
 * <p>
 * <pre>
 *     mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
 *             mLayoutManager.getOrientation());
 *     recyclerView.addItemDecoration(mDividerItemDecoration);
 * </pre>
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;
    public static final int EXCLUDE_POSITION_LAST = Integer.MAX_VALUE;

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private Drawable mDivider;

    /**
     * Current orientation. Either {@link #HORIZONTAL} or {@link #VERTICAL}.
     */
    private int mOrientation;

    private final Rect mBounds = new Rect();

    public DividerItemDecoration(Context context) {
        this(context, VERTICAL);
    }

    public DividerItemDecoration(Context context, int orientation) {
        mDivider = AppCompatResources.getDrawable(context, R.drawable.divider_default);
        setOrientation(orientation);
    }

    /**
     * Sets the orientation for this divider. This should be called if
     * {@link RecyclerView.LayoutManager} changes orientation.
     *
     * @param orientation {@link #HORIZONTAL} or {@link #VERTICAL}
     */
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException(
                    "Invalid orientation. It should be either HORIZONTAL or VERTICAL");
        }
        mOrientation = orientation;
    }

    /**
     * Sets the {@link Drawable} for this divider.
     *
     * @param drawable Drawable that should be used as a divider.
     */
    public void setDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
        mDivider = drawable;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    @SuppressLint("NewApi")
    protected void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int left;
        final int right;

        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        final int childCount = parent.getChildCount();
        final int adapterItemsCount = parent.getAdapter().getItemCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final int adapterPosition = findAdapterPositionForView(child, parent);
            if (isExcludePosition(adapterPosition, adapterItemsCount))
                continue; //skip excluded views positions

            final View childVertical = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(childVertical, mBounds);
            final int bottom = mBounds.bottom + Math.round(ViewCompat.getTranslationY(child));
            final int top = bottom - mDivider.getIntrinsicHeight();
            onSetDrawableBounds(mDivider, left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    @SuppressLint("NewApi")
    protected void drawHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int top;
        final int bottom;

        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        final int childCount = parent.getChildCount();
        final int adapterItemsCount = parent.getAdapter().getItemCount();

        for (int i = 0; i < childCount; i++) {
            final View childHorizontal = parent.getChildAt(i);

            final int adapterPosition = findAdapterPositionForView(childHorizontal, parent);
            if (isExcludePosition(adapterPosition, adapterItemsCount))
                continue; //skip excluded views positions

            parent.getLayoutManager().getDecoratedBoundsWithMargins(childHorizontal, mBounds);
            final int right = mBounds.right + Math.round(ViewCompat.getTranslationX(childHorizontal));
            final int left = right - mDivider.getIntrinsicWidth();
            onSetDrawableBounds(mDivider, left, top, right, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    protected void onSetDrawableBounds(Drawable drawable, int left, int top, int right, int bottom) {
        drawable.setBounds(left, top, right, bottom);
    }

    private int findAdapterPositionForView(View view, RecyclerView parent) {
        return parent.getChildViewHolder(view).getAdapterPosition();
    }

    private int[] mExcludePositions;

    public final void setExcludePositions(int... excludePositions) {
        mExcludePositions = excludePositions;
    }

    private boolean isExcludePosition(int position, int adapterItemsCount) {
        if (mExcludePositions == null) {
            return false;
        } else {
            for (int excludePosition : mExcludePositions) {
                if (position == excludePosition
                        || (position == adapterItemsCount - 1
                        && excludePosition == EXCLUDE_POSITION_LAST)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}