package net.avenwu.yoyogithub.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.avenwu.yoyogithub.adapter.RepoListAdapter;
import net.avenwu.yoyogithub.bean.Repo;
import net.avenwu.yoyogithub.presenter.Presenter;
import net.avenwu.yoyogithub.presenter.RepoPresenter;
import net.avenwu.yoyogithub.bean.Key;
import net.avenwu.yoyogithub.widget.RecyclerViewDecorator;

import java.util.List;

/**
 * Created by chaobin on 4/4/16.
 */
public class FragmentRepoList extends BaseFragment<RepoPresenter> implements RecyclerViewDecorator.Callback {
    private String mUser;
    private RecyclerViewDecorator mHelper;

    public FragmentRepoList() {
    }

    public static FragmentRepoList newInstance(String user) {
        FragmentRepoList fragment = new FragmentRepoList();
        Bundle args = new Bundle();
        args.putString(Key.USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = getArguments().getString(Key.USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        return new RecyclerViewDecorator.Builder(this)
            .withLoadMore(false)
            .withRefresh(true)
            .withEmptyLayout(true)
            .create(getActivity())
            .getView();
    }

    @Override
    public RecyclerView.Adapter onCreateAdapter() {
        return new RepoListAdapter();
    }

    @Override
    public void onViewPrepared(RecyclerViewDecorator helper) {
        mHelper = helper;
        if (mHelper.getEmptyLayout() != null) {
            mHelper.getEmptyLayout().setBackgroundColor(Color.WHITE);
        }
        RecyclerViewDecorator.setVerticalLine(helper.getRecyclerView(), 1);
        presenter(new Presenter.Action<RepoPresenter>() {
            @Override
            public void onRender(RepoPresenter data) {
                data.addAction(Presenter.ACTION_1, new Presenter.Action<List<Repo>>() {
                    @Override
                    public void onRender(List<Repo> data) {
                        mHelper.stopRefreshing();
                        ((RepoListAdapter) mHelper.getRecyclerView().getAdapter()).addDataList(data, false);
                    }
                }).addAction(Presenter.ACTION_2, new Presenter.Action<String>() {
                    @Override
                    public void onRender(String data) {
                        mHelper.stopRefreshing();
                        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
                    }
                });
                mHelper.startLoading();
                data.fetchRepo(mUser);
            }
        });

    }

    @Override
    public void onRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        presenter(new Presenter.Action<RepoPresenter>() {
            @Override
            public void onRender(RepoPresenter data) {
                data.fetchRepo(mUser);
            }
        });
    }

    @Override
    public void onLoadMore() {

    }
}
