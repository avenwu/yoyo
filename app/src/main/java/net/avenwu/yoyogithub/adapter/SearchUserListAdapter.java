package net.avenwu.yoyogithub.adapter;

import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.Toast;

import net.avenwu.yoyogithub.BR;
import net.avenwu.yoyogithub.R;
import net.avenwu.yoyogithub.bean.SearchUserResult;
import net.avenwu.yoyogithub.bean.ShortUserInfo;

public class SearchUserListAdapter extends DataBindingRecyclerViewAdapter<SearchUserResult.Item> {

    private UserClickListener mItemListener = new UserClickListener();

    @Override
    protected int onLayoutBinding() {
        return R.layout.search_user_item_layout;
    }

    @Override
    protected void onDataBinding(ViewDataBinding dataBinding, int position) {
        dataBinding.setVariable(BR.user, getItem(position));
        dataBinding.setVariable(BR.listener, mItemListener);
    }

    public static class UserClickListener {
        public void onClickItem(View view) {
            if (view.getTag() instanceof String) {
                Toast.makeText(view.getContext(), (String) view.getTag(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
