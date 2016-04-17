package net.avenwu.yoyogithub.bean;

/*
{
  "total_count": 40,
  "incomplete_results": false,
  "items": [
    {
      "id": 3081286,
      "name": "Tetris",
      "full_name": "dtrupenn/Tetris",
      "owner": {
        "login": "dtrupenn",
        "id": 872147,
        "avatar_url": "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",
        "gravatar_id": "",
        "url": "https://api.github.com/users/dtrupenn",
        "received_events_url": "https://api.github.com/users/dtrupenn/received_events",
        "type": "User"
      },
      "private": false,
      "html_url": "https://github.com/dtrupenn/Tetris",
      "description": "A C implementation of Tetris using Pennsim through LC4",
      "fork": false,
      "url": "https://api.github.com/repos/dtrupenn/Tetris",
      "created_at": "2012-01-01T00:31:50Z",
      "updated_at": "2013-01-05T17:58:47Z",
      "pushed_at": "2012-01-01T00:37:02Z",
      "homepage": "",
      "size": 524,
      "stargazers_count": 1,
      "watchers_count": 1,
      "language": "Assembly",
      "forks_count": 0,
      "open_issues_count": 0,
      "master_branch": "master",
      "default_branch": "master",
      "score": 10.309712
    }
  ]
}

 */

import java.util.List;

/**
 * Created by aven on 4/17/16.
 */
public class SearchRepoResult {
    public long total_count;
    public boolean incomplete_results;
    public List<Item> items;

    public static class Item {
        public long id;
        public String name;
        public String full_name;
        public Owner owner;
        public String html_url;
        public String description;
        public boolean fork;
        public String created_at;
        public String updated_at;
        public String pushed_at;
        public long size;
        public long stargazers_count;
        public long watchers_count;
        public String languagel;
        public long forks_count;
        public long open_issues_count;
        public String master_branch;
        public String default_branch;
        public float score;
    }

    public static class Owner {
        public String login;
        public long id;
        public String avatar_url;
        public String url;
        public String received_events_url;
        public String type;
    }
}
