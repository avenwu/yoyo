package net.avenwu.yoyogithub.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import net.avenwu.yoyogithub.presenter.Presenter;

import java.lang.reflect.ParameterizedType;

/**
 * Created by aven on 3/30/16.
 */
public class BaseActivity<P extends Presenter> extends AppCompatActivity {
    private P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mPresenter = (P) (((Class) ((ParameterizedType) this.getClass().
                    getGenericSuperclass()).getActualTypeArguments()[0]).newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (mPresenter != null) {
            mPresenter.attach(this);
        }
    }

    /**
     * safety invoke;
     *
     * @param action
     */
    public void presenter(Presenter.Action<P> action) {
        if (mPresenter != null) {
            action.onRender(mPresenter);
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detach(this);
        }
        super.onDestroy();
    }
}
