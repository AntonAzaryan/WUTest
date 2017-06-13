package com.wunderground.weatherunderground.util;

/**
 * Created by anton on 19.01.17.
 */

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.support.v7.widget.RecyclerView;

import com.wunderground.weatherunderground.archi.adapter.BindingAdapterDelegate;
import com.wunderground.weatherunderground.archi.mvvm.BindingRecyclerAdapter;
import com.wunderground.weatherunderground.archi.mvvm.MvvmItemView;
import com.wunderground.weatherunderground.ui.widget.decoration.ItemDecorationFactory;

import java.util.List;

public class BindingRecyclerViewAdapters {

    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"delegate", "adapter", "dataList", "viewHolder", "itemView"}, requireAll = false)
    public static <T> void setAdapter(RecyclerView recyclerView,
                                      BindingAdapterDelegate<T> delegate,
                                      BindingRecyclerViewAdapterFactory adapterFactory,
                                      List<T> dataList,
                                      BindingRecyclerAdapter.ViewHolderFactory viewHolderFactory,
                                      MvvmItemView.Factory itemViewFactory) {
        if (delegate == null) {
            throw new IllegalArgumentException("BindingAdapterDelegate must not be null");
        }
        if (adapterFactory == null) {
            adapterFactory = BindingRecyclerViewAdapterFactory.DEFAULT;
        }
        BindingRecyclerAdapter<T> adapter = (BindingRecyclerAdapter<T>) recyclerView.getAdapter();
        if (adapter == null) {
            adapter = adapterFactory.create(recyclerView, delegate);
            adapter.setDataList(dataList);
            adapter.setViewHolderFactory(viewHolderFactory);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setDataList(dataList);
        }
    }

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView, LayoutManagers.LayoutManagerFactory layoutManagerFactory) {
        recyclerView.setLayoutManager(layoutManagerFactory.create(recyclerView));
    }

    @BindingConversion
    public static BindingRecyclerViewAdapterFactory toRecyclerViewAdapterFactory(final String className) {
        return new BindingRecyclerViewAdapterFactory() {
            @Override
            public <T> BindingRecyclerAdapter<T> create(RecyclerView recyclerView, BindingAdapterDelegate<T> arg) {
                try {
                    return (BindingRecyclerAdapter<T>) Class.forName(className).getConstructor(BindingAdapterDelegate.class).newInstance(arg);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @BindingAdapter({"decorations"})
    public static void setItemDecorations(RecyclerView recyclerView, RecyclerView.ItemDecoration[] itemDecorators) {
        if (itemDecorators != null) {
            for (RecyclerView.ItemDecoration decoration : itemDecorators) {
                recyclerView.addItemDecoration(decoration);
            }
        }
    }

    @BindingAdapter({"decoration"})
    public static void setItemDecoration(RecyclerView recyclerView, RecyclerView.ItemDecoration itemDecorator) {
        if (itemDecorator != null) {
            recyclerView.addItemDecoration(itemDecorator);
        }
    }

    @BindingAdapter({"decoration"})
    public static void setItemDecoration(RecyclerView recyclerView, ItemDecorationFactory decorationFactory) {
        if (decorationFactory != null) {
            recyclerView.addItemDecoration(decorationFactory.create(recyclerView.getContext()));
        }
    }

}