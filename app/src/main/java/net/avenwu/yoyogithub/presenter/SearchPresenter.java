package net.avenwu.yoyogithub.presenter;

import net.avenwu.yoyogithub.api.GitHub;
import net.avenwu.yoyogithub.bean.SearchRepoResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Chaobin Wu on 6/16/16.
 */

public class SearchPresenter extends Presenter {

    public void searchRepo(String key) {
        GitHub.api().searchRepo(key, "forks", "desc").enqueue(new Callback<SearchRepoResult>() {
            @Override
            public void onResponse(Call<SearchRepoResult> call, Response<SearchRepoResult> response) {
                invokeAction(ACTION_1, response.body());
            }

            @Override
            public void onFailure(Call<SearchRepoResult> call, Throwable t) {
                invokeAction(ACTION_2, "Search repositories failed");
            }
        });
    }
}
