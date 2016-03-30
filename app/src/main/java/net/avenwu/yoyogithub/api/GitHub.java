package net.avenwu.yoyogithub.api;

import net.avenwu.yoyogithub.BuildConfig;
import net.avenwu.yoyogithub.model.Repo;
import net.avenwu.yoyogithub.model.User;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by aven on 3/30/16.
 */
public class GitHub {
    static GitHubService service;

    public static GitHubService api() {
        if (service == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder()
                                    .header("Accept", "application/vnd.github.v3+json")
                                    .build();
                            return chain.proceed(request);
                        }
                    });
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(logging);
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .client(builder.build())
                    .build();
            service = retrofit.create(GitHubService.class);
        }
        return service;
    }

    public interface GitHubService {

        @GET("users/{user}")
        Call<User> users(@Path("user") String user);

        @GET("users/{user}/repos")
        Call<List<Repo>> repos(@Path("user") String user);
    }
}
