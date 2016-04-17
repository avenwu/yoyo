package net.avenwu.yoyogithub;

import android.app.Application;
import android.test.ApplicationTestCase;

import net.avenwu.yoyogithub.api.GitHub;
import net.avenwu.yoyogithub.bean.Entry;
import net.avenwu.yoyogithub.bean.FeedUrl;
import net.avenwu.yoyogithub.bean.Repo;
import net.avenwu.yoyogithub.bean.SearchRepoResult;
import net.avenwu.yoyogithub.bean.ShortUserInfo;
import net.avenwu.yoyogithub.bean.User;
import net.avenwu.yoyogithub.bean.XmlFeedTimeline;

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
        Call<List<Repo>> call = service.repos("avenwu"/*, 1, 10*/);
        List<Repo> repoList = call.execute().body();
        assertNotNull(repoList);
        assertEquals(true, repoList.size() > 0);
        assertNotNull(repoList.get(0).owner);
        assertEquals("avenwu", repoList.get(0).owner.login);
    }

    public void testFollowers() throws Exception {
        Call<List<ShortUserInfo>> call = service.followers("avenwu");
        List<ShortUserInfo> shortUserInfos = call.execute().body();
        assertNotNull(shortUserInfos);
        assertEquals(true, shortUserInfos.size() > 0);
    }

    public void testFeed() throws Exception {
        FeedUrl feedUrl = service.feedUrl().execute().body();
        assertNotNull(feedUrl);
        assertNotNull(feedUrl.user_url);
        String userFeedUrl = feedUrl.userFeedUrl("avenwu");
        XmlFeedTimeline timeline = service.userFeed(userFeedUrl).execute().body();
        assertNotNull(timeline);
        assertNotNull(timeline.list);
        assertTrue(timeline.list.size() > 0);
        assertNotNull(timeline.title);
        assertNotNull(timeline.updated);
        for (Entry entry : timeline.list) {
            assertNotNull(entry.id);
            assertNotNull(entry.content);
            assertNotNull(entry.link);
            assertNotNull(entry.published);
            assertNotNull(entry.title);
            assertNotNull(entry.author);
            assertNotNull(entry.author.email);
            assertNotNull(entry.author.name);
            assertNotNull(entry.author.uri);
        }
    }

    public void testSearchRepo() throws Exception {
        SearchRepoResult result = service.searchRepo("tetris+language", "stars", "desc").execute().body();
        assertNotNull(result);
        assertTrue(result.total_count > 0);
        assertNotNull(result.items);
        for (SearchRepoResult.Item item : result.items) {
            assertNotNull(item);
            assertNotNull(item.full_name);
            assertNotNull(item.owner);
            assertNotNull(item.description);
        }

    }
}