package net.avenwu.yoyogithub.api;

import android.os.SystemClock;
import android.util.Log;

import net.avenwu.yoyogithub.BuildConfig;
import net.avenwu.yoyogithub.bean.FeedUrl;
import net.avenwu.yoyogithub.bean.Repo;
import net.avenwu.yoyogithub.bean.SearchRepoResult;
import net.avenwu.yoyogithub.bean.SearchUserResult;
import net.avenwu.yoyogithub.bean.ShortUserInfo;
import net.avenwu.yoyogithub.bean.User;
import net.avenwu.yoyogithub.bean.XmlFeedTimeline;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by aven on 3/30/16.
 */
public class GitHub {
    /**
     * static holder is kind of way to make service as singleton
     */
    private static class GitHubHolder {
        static GitHubService service;

        static {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request().newBuilder()
                                    .header("Accept", "application/vnd.github.v3+json")
                                    .header("Time-Zone", "Asia/Shanghai")
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS);
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(logging);
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GitHubConverterFactory.create().xmlBean(XmlFeedTimeline.class))
                    .client(builder.build())
                    .build();
            service = retrofit.create(GitHubService.class);
            Log.d("GitHub", "service init:" + SystemClock.elapsedRealtime());
        }
    }

    public static GitHubService api() {
        Log.d("GitHub", "call api:" + SystemClock.elapsedRealtime());
        return GitHubHolder.service;
    }

    public interface GitHubService {

        @GET("users/{user}")
        Call<User> users(@Path("user") String user);

        @GET("users/{user}/repos")
        Call<List<Repo>> repos(@Path("user") String user/*, @Query("page") int page, @Query("per_page")int per_page*/);

        @GET("users/{user}/followers")
        Call<List<ShortUserInfo>> followers(@Path("user") String user);

        @GET("users/{user}/following")
        Call<List<ShortUserInfo>> following(@Path("user") String user);

        @GET("feeds")
        Call<FeedUrl> feedUrl();

        /**
         * @param q     string	The search keywords, as well as any qualifiers.
         * @param sort  string  One of stars, forks, or updated. Default: results are sorted by best match.
         * @param order string	The sort order if sort parameter is provided. One of asc or desc. Default: desc
         * @return
         */
        @GET("search/repositories")
        Call<SearchRepoResult> searchRepo(@Query("q") String q, @Query("sort") String sort, @Query("order") String order);

        @GET("search/users")
        Call<SearchUserResult> searchUser(@Query("q") String q, @Query("sort") String sort, @Query("order") String order);

        @GET
        Call<XmlFeedTimeline> userFeed(@Url String url);

    }
}
