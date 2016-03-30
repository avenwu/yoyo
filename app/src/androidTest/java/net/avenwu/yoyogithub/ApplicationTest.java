package net.avenwu.yoyogithub;

import android.app.Application;
import android.test.ApplicationTestCase;

import net.avenwu.yoyogithub.api.GitHubService;
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

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {


    GitHubService service;

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .header("Accept", "application/vnd.github.v3+json")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(httpClient)
                .build();
        service = retrofit.create(GitHubService.class);
    }

    public void testUser() throws Exception {
        Call<User> call = service.users("avenwu");
        User user = call.execute().body();
        assertNotNull(user);
        assertEquals("Aven Wu", user.name);
        assertEquals(1622234, user.id);
    }

    public void testRepo() throws Exception {
        Call<List<Repo>> call = service.repos("avenwu");
        List<Repo> repoList = call.execute().body();
        assertNotNull(repoList);
        assertEquals(true, repoList.size() > 0);
        assertNotNull(repoList.get(0).owner);
        assertEquals("avenwu", repoList.get(0).owner.login);
    }

}