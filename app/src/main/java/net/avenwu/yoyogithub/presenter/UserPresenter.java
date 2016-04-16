package net.avenwu.yoyogithub.presenter;

import net.avenwu.yoyogithub.api.GitHub;
import net.avenwu.yoyogithub.bean.ShortUserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aven on 3/31/16.
 */
public class UserPresenter extends Presenter {

    public void fetchFollowers(String user) {
        GitHub.api().followers(user).enqueue(new Callback<List<ShortUserInfo>>() {

            @Override
            public void onResponse(Call<List<ShortUserInfo>> call, Response<List<ShortUserInfo>> response) {
                invokeAction(ACTION_1, response.body());
            }

            @Override
            public void onFailure(Call<List<ShortUserInfo>> call, Throwable t) {
                invokeAction(ACTION_2, "Fetch follower failed");
            }
        });
    }

    public void fetchFollowing(String user) {
        GitHub.api().following(user).enqueue(new Callback<List<ShortUserInfo>>() {

            @Override
            public void onResponse(Call<List<ShortUserInfo>> call, Response<List<ShortUserInfo>> response) {
                invokeAction(ACTION_1, response.body());
            }

            @Override
            public void onFailure(Call<List<ShortUserInfo>> call, Throwable t) {
                invokeAction(ACTION_2, "Fetch follower failed");
            }
        });
    }
}
