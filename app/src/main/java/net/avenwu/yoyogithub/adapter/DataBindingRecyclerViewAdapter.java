package net.avenwu.yoyogithub.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aven on 4/16/16.
 */
public abstract class DataBindingRecyclerViewAdapter<T> extends RecyclerView.Adapter<DataBindingViewHolder> {
    private final List<T> mValues = new ArrayList<>();

    @Override
    public DataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DataBindingViewHolder(parent, onLayoutBinding());
    }


    @Override
    public void onBindViewHolder(DataBindingViewHolder holder, int position) {
        onDataBinding(holder.getBinding(), position);
        holder.getBinding().executePendingBindings();
    }


    @LayoutRes
    protected abstract int onLayoutBinding();

    protected abstract void onDataBinding(ViewDataBinding dataBinding, int position);

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Nullable
    public T getItem(int position) {
        if (mValues.size() > position) {
            return mValues.get(position);
        }
        return null;
    }

    public void addDataList(List<T> list, boolean append) {
        if (append) {
            mValues.addAll(list);
        } else {
            mValues.clear();
            mValues.addAll(list);
        }
        notifyDataSetChanged();
    }

}
