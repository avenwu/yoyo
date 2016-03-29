package net.avenwu.yoyogithub.api;

import net.avenwu.yoyogithub.model.Repo;
import net.avenwu.yoyogithub.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by aven on 3/29/16.
 */
public interface GitHubService {

    @GET("users/{user}")
    Call<User> users(@Path("user") String user);

    @GET("users/{user}/repos")
    Call<List<Repo>> repos(@Path("user") String user);
}
