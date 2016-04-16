package net.avenwu.yoyogithub.adapter;

import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.Toast;

import net.avenwu.yoyogithub.BR;
import net.avenwu.yoyogithub.R;
import net.avenwu.yoyogithub.bean.Entry;

/**
 * Created by aven on 4/16/16.
 */
public class UserFeedAdapter extends DataBindingRecyclerViewAdapter<Entry> {
    FeedClickListener mListener = new FeedClickListener();

    @Override
    protected int onLayoutBinding() {
        return R.layout.user_feed_item_layout;
    }

    @Override
    protected void onDataBinding(ViewDataBinding dataBinding, int position) {
        dataBinding.setVariable(BR.feed, getItem(position));
        dataBinding.setVariable(BR.listener, mListener);
    }

    public static class FeedClickListener {
        public void onClickItem(View view) {
            if (view.getTag() instanceof String) {
                Toast.makeText(view.getContext(), (String) view.getTag(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
