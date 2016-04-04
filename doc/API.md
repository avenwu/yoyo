# API document
We use api provided by GitHub developer team, for more detailed information see [the website][1]
You can also use curl to get api snapshot quickly from CLI:

    curl https://api.github.com

the response contains most supported api in json format.

## User information

    /users/{user}
## Repo list

    users/{user}/repos
## Follower count

    /users/{user}/followers
## Following count

    /users/{user}/following

 [1]: https://developer.github.com/v3/
