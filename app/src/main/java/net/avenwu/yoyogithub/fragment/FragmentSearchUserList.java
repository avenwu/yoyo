package net.avenwu.yoyogithub.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.avenwu.yoyogithub.adapter.SearchRepoListAdapter;
import net.avenwu.yoyogithub.adapter.SearchUserListAdapter;
import net.avenwu.yoyogithub.bean.SearchRepoResult;
import net.avenwu.yoyogithub.bean.SearchUserResult;
import net.avenwu.yoyogithub.presenter.Presenter;
import net.avenwu.yoyogithub.presenter.SearchPresenter;
import net.avenwu.yoyogithub.widget.RecyclerViewDecorator;

/**
 * Created by Chaobin Wu on 6/16/16.
 */

public class FragmentSearchUserList extends BaseFragment<SearchPresenter> implements RecyclerViewDecorator.Callback {
    private String mKeyword;
    private RecyclerViewDecorator mHelper;
    private boolean mIsRefresh = true;

    public FragmentSearchUserList() {
    }

    public static FragmentSearchUserList newInstance(String keyword) {
        FragmentSearchUserList fragment = new FragmentSearchUserList();
        Bundle args = new Bundle();
        args.putString("keyword", keyword);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mKeyword = getArguments().getString("keyword");
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
        return new SearchUserListAdapter();
    }

    @Override
    public void onViewPrepared(RecyclerViewDecorator helper) {
        mHelper = helper;
        if (mHelper.getEmptyLayout() != null) {
            mHelper.getEmptyLayout().setBackgroundColor(Color.WHITE);
        }
        RecyclerViewDecorator.setVerticalLine(helper.getRecyclerView(), 1);
        presenter(new Presenter.Action<SearchPresenter>() {
            @Override
            public void onRender(SearchPresenter data) {
                data.addAction(Presenter.ACTION_1, new Presenter.Action<SearchUserResult>() {
                    @Override
                    public void onRender(SearchUserResult data) {
                        mHelper.stopRefreshing();
                        if (data.items != null && !data.items.isEmpty()) {
                            ((SearchUserListAdapter) mHelper.getRecyclerView().getAdapter()).addDataList(data.items, !mIsRefresh);
                        }
                        mIsRefresh = false;
                    }
                }).addAction(Presenter.ACTION_2, new Presenter.Action<String>() {
                    @Override
                    public void onRender(String data) {
                        mHelper.stopRefreshing();
                        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
                    }
                });
                mHelper.startLoading();
                data.searchUser(mKeyword);
            }
        });
    }

    @Override
    public void onRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        presenter(new Presenter.Action<SearchPresenter>() {
            @Override
            public void onRender(SearchPresenter data) {
                mIsRefresh = true;
                data.searchUser(mKeyword);
            }
        });
    }

    @Override
    public void onLoadMore() {

    }
}
