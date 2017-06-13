package com.wunderground.weatherunderground.archi.mvvm;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wunderground.weatherunderground.archi.adapter.BindingAdapterDelegate;
import com.wunderground.weatherunderground.archi.adapter.ItemView;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

/**
 * Created by Anton Azaryan on 10.01.2017.
 */

public class BindingRecyclerAdapter<D> extends RecyclerView.Adapter<BindingRecyclerAdapter.BindingViewHolder<D>> {

    private final BindingAdapterDelegate<D> bindingAdapterDelegate;
    private final WeakReferenceOnListChangedCallback<D> observableListCallback =
            new WeakReferenceOnListChangedCallback<>(this);

    private List<D> dataList = Collections.emptyList();
    private LayoutInflater inflater;
    private ViewHolderFactory viewHolderFactory;

    @Nullable
    private RecyclerView recyclerView;

    public BindingRecyclerAdapter(BindingAdapterDelegate<D> bindingAdapterDelegate) {
        this.bindingAdapterDelegate = bindingAdapterDelegate;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (this.recyclerView == null && dataList instanceof ObservableList) {
            ((ObservableList<D>) dataList).addOnListChangedCallback(observableListCallback);
        }
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        if (this.recyclerView != null && dataList instanceof ObservableList) {
            ((ObservableList<D>) dataList).removeOnListChangedCallback(observableListCallback);
        }
        this.recyclerView = null;
    }

    public void setDataList(@Nullable List<D> dataList) {
        if (dataList == null || dataList == this.dataList)
            return;

        if (recyclerView != null) {
            if (this.dataList instanceof ObservableList) {
                ((ObservableList<D>) this.dataList).removeOnListChangedCallback(observableListCallback);
            }
            if (dataList instanceof ObservableList) {
                ((ObservableList<D>) dataList).addOnListChangedCallback(observableListCallback);
            }
        }
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public BindingViewHolder<D> onCreateViewHolder(ViewGroup parent, @LayoutRes int layoutId) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);

        BaseItemViewModel<?, D> viewModel = obtainItemViewModel(layoutId);
        BindingViewHolder<D> viewHolder = obtainViewHolder(binding);

        if (viewModel != null) {
            viewModel.setContext(parent.getContext());
            bindViewModelToLayout(viewHolder, viewModel);
        }
        return viewHolder;
    }

    private BaseItemViewModel<?, D> obtainItemViewModel(@LayoutRes int layoutResId) {
        return bindingAdapterDelegate.obtainViewModel(layoutResId);
    }

    private BindingViewHolder<D> obtainViewHolder(ViewDataBinding binding) {
        if (viewHolderFactory != null) {
            return viewHolderFactory.createViewHolder(binding);
        } else {
            return new BindingViewHolder<>(binding);
        }
    }

    void bindViewModelToLayout(BindingViewHolder<D> viewHolder, BaseItemViewModel<?, D> viewModel) {
        viewHolder.viewModel = viewModel;
        int bindingVariable = bindingAdapterDelegate.bindingVariable();

        if (bindingVariable != ItemView.BINDING_VARIABLE_NONE) {
            ViewDataBinding binding = viewHolder.binding;
            boolean viewModelBindResult = binding.setVariable(bindingAdapterDelegate.bindingVariable(), viewModel);
            if (!viewModelBindResult) {
                throw new IllegalStateException("Could not bind variable");
            }
            binding.executePendingBindings();
        }
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<D> holder, int position) {
        D dataItem = dataList.get(position);
        holder.bindDataModel(position, dataItem);
    }

    @Override
    public int getItemViewType(int position) {
        bindingAdapterDelegate.select(position, dataList.get(position));
        return bindingAdapterDelegate.layoutRes();
    }

    @Override
    public void onViewAttachedToWindow(BindingViewHolder<D> holder) {
        holder.onViewAttachedToWindow();
        holder.viewModel.attachMvvmView(holder);
    }

    @Override
    public void onViewDetachedFromWindow(BindingViewHolder<D> holder) {
        holder.viewModel.detachMvvmView();
        holder.onViewDetachedFromWindow();
    }

    @Override
    public void onViewRecycled(BindingViewHolder<D> holder) {
        holder.onViewRecycled();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setViewHolderFactory(ViewHolderFactory viewHolderFactory) {
        this.viewHolderFactory = viewHolderFactory;
    }

    public interface ViewHolderFactory {
        <D> BindingViewHolder<D> createViewHolder(ViewDataBinding dataBinding);
    }

    public static class BindingViewHolder<D> extends RecyclerView.ViewHolder implements MvvmItemView {

        final ViewDataBinding binding;
        BaseItemViewModel<?, D> viewModel;

        BindingViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindDataModel(int position, D dataModel) {
            if (viewModel != null) {
                viewModel.bindDataModel(position, dataModel);
            }
        }

        public BaseItemViewModel<?, D> getViewModel() {
            return viewModel;
        }

        @Override
        public void onViewRecycled() {
        }

        @Override
        public void onViewAttachedToWindow() {
        }

        @Override
        public void onViewDetachedFromWindow() {
        }

        @Override
        public void postMessage(Message message) {
        }
    }

    private class WeakReferenceOnListChangedCallback<T> extends ObservableList.OnListChangedCallback<ObservableList<T>> {

        private final WeakReference<BindingRecyclerAdapter<T>> adapterRef;
        private final Handler handler = new Handler();

        WeakReferenceOnListChangedCallback(BindingRecyclerAdapter<T> adapter) {
            this.adapterRef = new WeakReference<>(adapter);
        }

        @Override
        public void onChanged(ObservableList sender) {
            BindingRecyclerAdapter<T> adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            if (!isOnMainThread()) {
                handler.post(adapter::notifyDataSetChanged);
            } else {
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeChanged(ObservableList sender, final int positionStart, final int itemCount) {
            BindingRecyclerAdapter<T> adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }

            if (!isOnMainThread()) {
                handler.post(() -> adapter.notifyItemRangeChanged(positionStart, itemCount));
            } else {
                adapter.notifyItemRangeChanged(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeInserted(ObservableList sender, final int positionStart, final int itemCount) {
            BindingRecyclerAdapter<T> adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            if (!isOnMainThread()) {
                handler.post(() -> adapter.notifyItemRangeInserted(positionStart, itemCount));
            } else {
                adapter.notifyItemRangeInserted(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeMoved(ObservableList sender, final int fromPosition, final int toPosition, final int itemCount) {
            BindingRecyclerAdapter<T> adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }

            if (!isOnMainThread()) {
                handler.post(() -> {
                    for (int i = 0; i < itemCount; i++) {
                        adapter.notifyItemMoved(fromPosition + i, toPosition + i);
                    }
                });
            } else {
                for (int i = 0; i < itemCount; i++) {
                    adapter.notifyItemMoved(fromPosition + i, toPosition + i);
                }
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender, final int positionStart, final int itemCount) {
            BindingRecyclerAdapter<T> adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }

            if (!isOnMainThread()) {
                handler.post(() -> adapter.notifyItemRangeRemoved(positionStart, itemCount));
            } else {
                adapter.notifyItemRangeRemoved(positionStart, itemCount);
            }
        }

        boolean isOnMainThread() {
            return Thread.currentThread() == Looper.getMainLooper().getThread();
        }
    }

}
