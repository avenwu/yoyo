package net.avenwu.yoyogithub.adapter;

import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.Toast;

import net.avenwu.yoyogithub.BR;
import net.avenwu.yoyogithub.R;
import net.avenwu.yoyogithub.bean.Repo;

public class RepoListAdapter extends DataBindingRecyclerViewAdapter<Repo> {

    private RepoClickListener mItemListener = new RepoClickListener();

    @Override
    protected int onLayoutBinding() {
        return R.layout.repo_item_layout;
    }

    @Override
    protected void onDataBinding(ViewDataBinding dataBinding, int position) {
        dataBinding.setVariable(BR.repo, getItem(position));
        dataBinding.setVariable(BR.listener, mItemListener);
    }

    public static class RepoClickListener {
        public void onClickItem(View view) {
            if (view.getTag() instanceof String) {
                Toast.makeText(view.getContext(), (String) view.getTag(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
