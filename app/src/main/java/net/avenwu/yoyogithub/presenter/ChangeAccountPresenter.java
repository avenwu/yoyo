package net.avenwu.yoyogithub.presenter;

import net.avenwu.yoyogithub.api.GitHub;
import net.avenwu.yoyogithub.bean.User;

import retrofit2.Callback;

/**
 * Created by Chaobin Wu on 6/2/16.
 */
public class ChangeAccountPresenter extends Presenter {

    public void checkAccount(String name, Callback<User> callback) {
        GitHub.api().users(name).enqueue(callback);
    }
}
