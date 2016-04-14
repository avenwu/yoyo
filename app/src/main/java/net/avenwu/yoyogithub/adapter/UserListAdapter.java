package net.avenwu.yoyogithub.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.avenwu.yoyogithub.BR;
import net.avenwu.yoyogithub.R;
import net.avenwu.yoyogithub.bean.ShortUserInfo;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<DataBindingViewHolder> {

    private final List<ShortUserInfo> mValues = new ArrayList<>();
    private UserClickListener mItemListener = new UserClickListener();

    @Override
    public DataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DataBindingViewHolder(parent, R.layout.user_item_layout);
    }

    @Override
    public void onBindViewHolder(final DataBindingViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.user, mValues.get(position));
        holder.getBinding().setVariable(BR.listener, mItemListener);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addDataList(List<ShortUserInfo> list, boolean append) {
        if (append) {
            mValues.addAll(list);
        } else {
            mValues.clear();
            mValues.addAll(list);
        }
        notifyDataSetChanged();
    }


    public static class UserClickListener {
        public void onClickItem(View view) {
            if (view.getTag() instanceof String) {
                Toast.makeText(view.getContext(), (String) view.getTag(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
