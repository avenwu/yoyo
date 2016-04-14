package net.avenwu.yoyogithub.presenter;

import android.util.SparseArray;

import net.avenwu.yoyogithub.annotation.ActionKey;

import java.lang.ref.WeakReference;

/**
 * Created by aven on 3/30/16.
 */
public class Presenter {
    Object mTarget;
    SparseArray<Action> mActionList;
    SparseArray<WeakReference<Action>> mCachedAction;
    @ActionKey
    public static final int ACTION_1 = 110;
    @ActionKey
    public static final int ACTION_2 = 120;

    public <T> void attach(T target) {
        mTarget = target;
        if (mCachedAction != null && mCachedAction.size() > 0) {
            if (mActionList == null) {
                mActionList = new SparseArray<>(mCachedAction.size());
            }
            for (int i = 0; i < mCachedAction.size(); i++) {
                int key = mCachedAction.keyAt(i);
                WeakReference<Action> actionWeakReference = mCachedAction.get(key);
                if (actionWeakReference != null && actionWeakReference.get() != null) {
                    mActionList.put(key, actionWeakReference.get());
                }
            }
            mCachedAction.clear();
        }
    }

    public <T> void detach(T target) {
        if (mActionList != null && mActionList.size() > 0) {
            if (mCachedAction == null) {
                mCachedAction = new SparseArray<>(mActionList.size());
            }
            for (int i = 0; i < mActionList.size(); i++) {
                int key = mActionList.keyAt(i);
                Action action = mActionList.get(key);
                mCachedAction.put(key, new WeakReference<Action>(action));
            }
            mActionList.clear();
        }
        mTarget = null;
    }

    public <T> Presenter addAction(@ActionKey int key, Action<T> action) {
        if (mActionList == null) {
            mActionList = new SparseArray<>();
        }
        mActionList.put(key, action);
        return this;
    }

    public <T> void invokeAction(@ActionKey int key, T data) {
        if (mActionList != null) {
            Action action = mActionList.get(key);
            if (action != null && mTarget != null) {
                action.onRender(data);
            }
        }
    }

    public interface Action<T> {
        void onRender(T data);
    }
}
