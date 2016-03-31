package net.avenwu.yoyogithub.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.avenwu.yoyogithub.adapter.UserListAdapter;
import net.avenwu.yoyogithub.model.ShortUserInfo;
import net.avenwu.yoyogithub.presenter.Presenter;
import net.avenwu.yoyogithub.presenter.UserPresenter;
import net.avenwu.yoyogithub.widget.RecyclerViewDecorator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * User list:follower or following
 */
public class FragmentUserList extends BaseFragment<UserPresenter> implements RecyclerViewDecorator.Callback {

    private static final String KEY_USER = "user";
    private static final String KEY_TYPE = "type";
    private String mUser;
    private int mType;
    private RecyclerViewDecorator mHelper;

    @UserListType
    public static final int FOLLOWER = 0;
    @UserListType
    public static final int FOLLOWING = 1;

    @Retention(CLASS)
    @Target({METHOD, PARAMETER, FIELD, LOCAL_VARIABLE})
    public @interface UserListType {

    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentUserList() {
    }

    public static FragmentUserList newInstance(String user, @UserListType int type) {
        FragmentUserList fragment = new FragmentUserList();
        Bundle args = new Bundle();
        args.putString(KEY_USER, user);
        args.putInt(KEY_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = getArguments().getString(KEY_USER);
            mType = getArguments().getInt(KEY_TYPE);
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
        return new UserListAdapter();
    }

    @Override
    public void onViewPrepared(RecyclerViewDecorator helper) {
        mHelper = helper;
        if (mHelper.getEmptyLayout() != null) {
            mHelper.getEmptyLayout().setBackgroundColor(Color.WHITE);
        }
        presenter(new Presenter.Action<UserPresenter>() {
            @Override
            public void onRender(UserPresenter data) {
                data.addAction(Presenter.ACTION_1, new Presenter.Action<List<ShortUserInfo>>() {
                    @Override
                    public void onRender(List<ShortUserInfo> data) {
                        mHelper.stopRefreshing();
                        ((UserListAdapter) mHelper.getRecyclerView().getAdapter()).addDataList(data, false);
                    }
                });

                data.addAction(Presenter.ACTION_2, new Presenter.Action<String>() {
                    @Override
                    public void onRender(String data) {
                        mHelper.stopRefreshing();
                        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
                    }
                });
                mHelper.startLoading();
                fetchData(data);
            }
        });
    }

    private void fetchData(UserPresenter data) {
        switch (mType) {
            case FOLLOWER:
                data.fetchFollowers(mUser);
                break;
            case FOLLOWING:
                data.fetchFollowing(mUser);
                break;
        }
    }

    @Override
    public void onRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        presenter(new Presenter.Action<UserPresenter>() {
            @Override
            public void onRender(UserPresenter data) {
                fetchData(data);
            }
        });
    }

    @Override
    public void onLoadMore() {

    }
}
