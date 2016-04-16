package net.avenwu.yoyogithub.bean;
/*
{
  "timeline_url": "https://github.com/timeline",
  "user_url": "https://github.com/{user}",
  "_links": {
    "timeline": {
      "href": "https://github.com/timeline",
      "type": "application/atom+xml"
    },
    "user": {
      "href": "https://github.com/{user}",
      "type": "application/atom+xml"
    }
  }
}
 */

/**
 * Created by aven on 4/15/16.
 */
public class FeedUrl {
    public String timeline_url;
    public String user_url;

    public String userFeedUrl(String user) {
        if (user_url != null && user_url.contains("{user}")) {
            return user_url.replace("{user}", user + ".atom");
        }
        return user_url;
    }
}
