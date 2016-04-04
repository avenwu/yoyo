package net.avenwu.yoyogithub.presenter;

import net.avenwu.yoyogithub.api.GitHub;
import net.avenwu.yoyogithub.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chaobin on 4/4/16.
 */
public class RepoPresenter extends Presenter {
    public void fetchRepo(String user) {
        GitHub.api().repos(user).enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                invokeAction(ACTION_1, response.body());
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                invokeAction(ACTION_2, "Fetch repo failed");
            }
        });
    }
}
