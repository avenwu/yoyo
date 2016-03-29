package net.avenwu.yoyogithub.model;
/*
{
    "id": 54779440,
    "name": "algorithm-note",
    "full_name": "avenwu/algorithm-note",
    "owner": {
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
        "site_admin": false
    },
    "private": false,
    "html_url": "https://github.com/avenwu/algorithm-note",
    "description": "算法导论笔记",
    "fork": false,
    "url": "https://api.github.com/repos/avenwu/algorithm-note",
    "forks_url": "https://api.github.com/repos/avenwu/algorithm-note/forks",
    "keys_url": "https://api.github.com/repos/avenwu/algorithm-note/keys{/key_id}",
    "collaborators_url": "https://api.github.com/repos/avenwu/algorithm-note/collaborators{/collaborator}",
    "teams_url": "https://api.github.com/repos/avenwu/algorithm-note/teams",
    "hooks_url": "https://api.github.com/repos/avenwu/algorithm-note/hooks",
    "issue_events_url": "https://api.github.com/repos/avenwu/algorithm-note/issues/events{/number}",
    "events_url": "https://api.github.com/repos/avenwu/algorithm-note/events",
    "assignees_url": "https://api.github.com/repos/avenwu/algorithm-note/assignees{/user}",
    "branches_url": "https://api.github.com/repos/avenwu/algorithm-note/branches{/branch}",
    "tags_url": "https://api.github.com/repos/avenwu/algorithm-note/tags",
    "blobs_url": "https://api.github.com/repos/avenwu/algorithm-note/git/blobs{/sha}",
    "git_tags_url": "https://api.github.com/repos/avenwu/algorithm-note/git/tags{/sha}",
    "git_refs_url": "https://api.github.com/repos/avenwu/algorithm-note/git/refs{/sha}",
    "trees_url": "https://api.github.com/repos/avenwu/algorithm-note/git/trees{/sha}",
    "statuses_url": "https://api.github.com/repos/avenwu/algorithm-note/statuses/{sha}",
    "languages_url": "https://api.github.com/repos/avenwu/algorithm-note/languages",
    "stargazers_url": "https://api.github.com/repos/avenwu/algorithm-note/stargazers",
    "contributors_url": "https://api.github.com/repos/avenwu/algorithm-note/contributors",
    "subscribers_url": "https://api.github.com/repos/avenwu/algorithm-note/subscribers",
    "subscription_url": "https://api.github.com/repos/avenwu/algorithm-note/subscription",
    "commits_url": "https://api.github.com/repos/avenwu/algorithm-note/commits{/sha}",
    "git_commits_url": "https://api.github.com/repos/avenwu/algorithm-note/git/commits{/sha}",
    "comments_url": "https://api.github.com/repos/avenwu/algorithm-note/comments{/number}",
    "issue_comment_url": "https://api.github.com/repos/avenwu/algorithm-note/issues/comments{/number}",
    "contents_url": "https://api.github.com/repos/avenwu/algorithm-note/contents/{+path}",
    "compare_url": "https://api.github.com/repos/avenwu/algorithm-note/compare/{base}...{head}",
    "merges_url": "https://api.github.com/repos/avenwu/algorithm-note/merges",
    "archive_url": "https://api.github.com/repos/avenwu/algorithm-note/{archive_format}{/ref}",
    "downloads_url": "https://api.github.com/repos/avenwu/algorithm-note/downloads",
    "issues_url": "https://api.github.com/repos/avenwu/algorithm-note/issues{/number}",
    "pulls_url": "https://api.github.com/repos/avenwu/algorithm-note/pulls{/number}",
    "milestones_url": "https://api.github.com/repos/avenwu/algorithm-note/milestones{/number}",
    "notifications_url": "https://api.github.com/repos/avenwu/algorithm-note/notifications{?since,all,participating}",
    "labels_url": "https://api.github.com/repos/avenwu/algorithm-note/labels{/name}",
    "releases_url": "https://api.github.com/repos/avenwu/algorithm-note/releases{/id}",
    "deployments_url": "https://api.github.com/repos/avenwu/algorithm-note/deployments",
    "created_at": "2016-03-26T13:07:06Z",
    "updated_at": "2016-03-26T13:29:59Z",
    "pushed_at": "2016-03-27T09:53:17Z",
    "git_url": "git://github.com/avenwu/algorithm-note.git",
    "ssh_url": "git@github.com:avenwu/algorithm-note.git",
    "clone_url": "https://github.com/avenwu/algorithm-note.git",
    "svn_url": "https://github.com/avenwu/algorithm-note",
    "homepage": null,
    "size": 7,
    "stargazers_count": 0,
    "watchers_count": 0,
    "language": "Swift",
    "has_issues": true,
    "has_downloads": true,
    "has_wiki": true,
    "has_pages": false,
    "forks_count": 0,
    "mirror_url": null,
    "open_issues_count": 0,
    "forks": 0,
    "open_issues": 0,
    "watchers": 0,
    "default_branch": "master"
}
 */

/**
 * Created by aven on 3/29/16.
 */
public class Repo {
    public long id;
    public String name;
    public User owner;
    public String description;
    public String created_at;
    public String updated_at;
    public String git_url;
    public String clone_url;
    public String language;
    public long forks_count;
    public long open_issues_count;
    public long watchers_count;

}
