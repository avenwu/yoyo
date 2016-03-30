package net.avenwu.yoyogithub;

import android.app.Application;
import android.test.ApplicationTestCase;

import net.avenwu.yoyogithub.api.GitHub;
import net.avenwu.yoyogithub.model.Repo;
import net.avenwu.yoyogithub.model.User;

import java.util.List;

import retrofit2.Call;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {


    GitHub.GitHubService service;

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        service = GitHub.api();
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