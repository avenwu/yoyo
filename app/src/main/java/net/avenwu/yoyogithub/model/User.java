package net.avenwu.yoyogithub.model;
/*
{
  "login": "avenwu",
  "id": 1622234,
  "avatar_url": "https://avatars.githubusercontent.com/u/1622234?v=3",
  "gravatar_id": "",
  "url": "https://api.github.com/users/avenwu",
  "html_url": "https://github.com/avenwu",
  "followers_url": "https://api.github.com/users/avenwu/followers",
  "following_url": "https://api.github.com/users/avenwu/following{/other_user}",
  "gists_url": "https://api.github.com/users/avenwu/gists{/gist_id}",
  "starred_url": "https://api.github.com/users/avenwu/starred{/owner}{/repo}",
  "subscriptions_url": "https://api.github.com/users/avenwu/subscriptions",
  "organizations_url": "https://api.github.com/users/avenwu/orgs",
  "repos_url": "https://api.github.com/users/avenwu/repos",
  "events_url": "https://api.github.com/users/avenwu/events{/privacy}",
  "received_events_url": "https://api.github.com/users/avenwu/received_events",
  "type": "User",
  "site_admin": false,
  "name": "Aven Wu",
  "company": null,
  "blog": "http://avenwu.net/",
  "location": "Beijing, China",
  "email": "me@avenwu.net",
  "hireable": true,
  "bio": null,
  "public_repos": 37,
  "public_gists": 0,
  "followers": 46,
  "following": 13,
  "created_at": "2012-04-08T00:58:02Z",
  "updated_at": "2016-03-28T01:32:51Z"
}
 */

/**
 * Created by aven on 3/29/16.
 */
public class User {
    public String login;
    public long id;
    public String avatar_url;
    public String url;
    public String html_url;
    public String name;
    public String blog;
    public String location;
    public String email;
    public int public_repos;
    public long followers;
    public long following;
    public String created_at;
    public String updated_at;
}
