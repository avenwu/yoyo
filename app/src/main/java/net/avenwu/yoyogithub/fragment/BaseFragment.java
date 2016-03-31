package net.avenwu.yoyogithub.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import net.avenwu.yoyogithub.presenter.Presenter;

import java.lang.reflect.ParameterizedType;

/**
 * Created by aven on 3/31/16.
 */
public class BaseFragment<P extends Presenter> extends Fragment {
    private P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mPresenter = (P) (((Class) ((ParameterizedType) this.getClass().
                    getGenericSuperclass()).getActualTypeArguments()[0]).newInstance());
        } catch (java.lang.InstantiationException e) {
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
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detach(this);
        }
        super.onDestroy();
    }
}
