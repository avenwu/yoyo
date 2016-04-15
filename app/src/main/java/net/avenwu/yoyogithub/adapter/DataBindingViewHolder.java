package net.avenwu.yoyogithub.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DataBindingViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding binding;

    public DataBindingViewHolder(@NonNull ViewGroup parent, @LayoutRes int layout) {
        this(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    public DataBindingViewHolder(View view) {
        super(view);
        binding = DataBindingUtil.bind(view);
    }

    public ViewDataBinding getBinding() {
        return binding;
    }
}