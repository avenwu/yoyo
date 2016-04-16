package net.avenwu.yoyogithub.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.avenwu.yoyogithub.adapter.UserFeedAdapter;
import net.avenwu.yoyogithub.bean.Key;
import net.avenwu.yoyogithub.bean.XmlFeedTimeline;
import net.avenwu.yoyogithub.presenter.FeedPresenter;
import net.avenwu.yoyogithub.presenter.Presenter;
import net.avenwu.yoyogithub.widget.RecyclerViewDecorator;

/**
 * Created by aven on 4/16/16.
 */
public class FragmentUserFeedList extends BaseFragment<FeedPresenter> implements RecyclerViewDecorator.Callback {
    private String mUser;
    private RecyclerViewDecorator mHelper;

    public FragmentUserFeedList() {

    }

    public static FragmentUserFeedList newInstance(String user) {
        FragmentUserFeedList fragmentUserFeedList = new FragmentUserFeedList();
        Bundle args = new Bundle();
        args.putString(Key.USER, user);
        fragmentUserFeedList.setArguments(args);
        return fragmentUserFeedList;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = getArguments().getString(Key.USER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new RecyclerViewDecorator.Builder(this)
                .withLoadMore(false)
                .withRefresh(true)
                .withEmptyLayout(true)
                .create(getActivity())
                .getView();
    }

    @Override
    public RecyclerView.Adapter onCreateAdapter() {
        return new UserFeedAdapter();
    }

    @Override
    public void onViewPrepared(RecyclerViewDecorator helper) {
        mHelper = helper;
        if (mHelper.getEmptyLayout() != null) {
            mHelper.getEmptyLayout().setBackgroundColor(Color.WHITE);
        }
        RecyclerViewDecorator.setVerticalLine(mHelper.getRecyclerView(), 1);
        presenter(new Presenter.Action<FeedPresenter>() {
            @Override
            public void onRender(FeedPresenter data) {
                data.addAction(Presenter.ACTION_1, new Presenter.Action<XmlFeedTimeline>() {
                    @Override
                    public void onRender(XmlFeedTimeline data) {
                        mHelper.stopRefreshing();
                        ((UserFeedAdapter) mHelper.getRecyclerView().getAdapter()).addDataList(data.list, false);
                    }
                }).addAction(Presenter.ACTION_2, new Presenter.Action<String>() {
                    @Override
                    public void onRender(String data) {
                        mHelper.stopRefreshing();
                        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
                    }
                });
                mHelper.startLoading();
                data.feedOf(mUser);
            }
        });
    }

    @Override
    public void onRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        presenter(new Presenter.Action<FeedPresenter>() {
            @Override
            public void onRender(FeedPresenter data) {
                data.feedOf(mUser);
            }
        });
    }

    @Override
    public void onLoadMore() {

    }
}
