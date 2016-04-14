package net.avenwu.yoyogithub.presenter;

import net.avenwu.yoyogithub.api.GitHub;
import net.avenwu.yoyogithub.bean.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aven on 3/30/16.
 */
public class ProfilePresenter extends Presenter {

    public void fetchUserData(String user) {
        GitHub.api().users(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                invokeAction(ACTION_1, response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                invokeAction(ACTION_2, "Failed to get user data");
            }
        });
    }
}
