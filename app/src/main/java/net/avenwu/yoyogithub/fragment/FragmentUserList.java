package net.avenwu.yoyogithub.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.avenwu.yoyogithub.R;
import net.avenwu.yoyogithub.adapter.UserListAdapter;
import net.avenwu.yoyogithub.model.ShortUserInfo;
import net.avenwu.yoyogithub.presenter.Presenter;
import net.avenwu.yoyogithub.presenter.UserPresenter;

import java.util.List;

public class FragmentUserList extends BaseFragment<UserPresenter> {

    private static final String KEY_USER = "user";
    private String mUser;
    private RecyclerView mRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentUserList() {
    }

    public static FragmentUserList newInstance(String user) {
        FragmentUserList fragment = new FragmentUserList();
        Bundle args = new Bundle();
        args.putString(KEY_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = getArguments().getString(KEY_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_list_layout, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerView.setAdapter(new UserListAdapter());
        }
        presenter(new Presenter.Action<UserPresenter>() {
            @Override
            public void onRender(UserPresenter data) {
                data.addAction(Presenter.ACTION_1, new Presenter.Action<List<ShortUserInfo>>() {
                    @Override
                    public void onRender(List<ShortUserInfo> data) {
                        ((UserListAdapter) mRecyclerView.getAdapter()).addDataList(data);
                    }
                });

                data.addAction(Presenter.ACTION_2, new Presenter.Action<String>() {
                    @Override
                    public void onRender(String data) {
                        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
                    }
                });
                data.fetchFollowers(mUser);
            }
        });
        return view;
    }
}
