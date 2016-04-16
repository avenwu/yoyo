package net.avenwu.yoyogithub.presenter;

import net.avenwu.yoyogithub.api.GitHub;
import net.avenwu.yoyogithub.bean.FeedUrl;
import net.avenwu.yoyogithub.bean.XmlFeedTimeline;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aven on 4/16/16.
 */
public class FeedPresenter extends Presenter {
    public void feedOf(final String user) {
        GitHub.api().feedUrl().enqueue(new Callback<FeedUrl>() {
            @Override
            public void onResponse(Call<FeedUrl> call, Response<FeedUrl> response) {
                GitHub.api().userFeed(response.body().userFeedUrl(user)).enqueue(new Callback<XmlFeedTimeline>() {
                    @Override
                    public void onResponse(Call<XmlFeedTimeline> call, Response<XmlFeedTimeline> response) {
                        if (response.body() == null || response.body().list == null || response.body().list.isEmpty()) {
                            invokeAction(ACTION_2, "No feed found");
                        } else {
                            invokeAction(ACTION_1, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<XmlFeedTimeline> call, Throwable t) {
                        invokeAction(ACTION_2, "Failed to load timeline");
                    }
                });
            }

            @Override
            public void onFailure(Call<FeedUrl> call, Throwable t) {
                invokeAction(ACTION_2, "Failed to load timeline");
            }
        });

    }
}
