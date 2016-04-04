package net.avenwu.yoyogithub.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.avenwu.yoyogithub.BR;
import net.avenwu.yoyogithub.R;
import net.avenwu.yoyogithub.model.Repo;

import java.util.ArrayList;
import java.util.List;

public class RepoListAdapter extends RecyclerView.Adapter<DataBindingViewHolder> {

    private final List<Repo> mValues = new ArrayList<>();
    private RepoClickListener mItemListener = new RepoClickListener();

    @Override
    public DataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DataBindingViewHolder(parent, R.layout.repo_item_layout);
    }

    @Override
    public void onBindViewHolder(DataBindingViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.repo, mValues.get(position));
        holder.getBinding().setVariable(BR.listener, mItemListener);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addDataList(List<Repo> list, boolean append) {
        if (append) {
            mValues.addAll(list);
        } else {
            mValues.clear();
            mValues.addAll(list);
        }
        notifyDataSetChanged();
    }

    public static class RepoClickListener {
        public void onClickItem(View view) {
            if (view.getTag() instanceof String) {
                Toast.makeText(view.getContext(), (String) view.getTag(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
