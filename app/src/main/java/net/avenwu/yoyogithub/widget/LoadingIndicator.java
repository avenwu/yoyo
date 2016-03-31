package net.avenwu.yoyogithub.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import net.avenwu.yoyogithub.R;


/**
 * LoadingIndicator is designed inspired by RecyclerViewHeader which created by Bartosz Lipinski
 * <p>
 * Created by chaobin on 7/20/15.
 */
public class LoadingIndicator {
    int[] mPositions = {0, 0};
    int mCurrentScroll;

    State mState = State.IDLE;
    OnLastItemVisible mLastItemVisible;
    OnStatusChanged mOnStatusChanged;
    ViewGroup mLayout;
    ProgressBar mProgressbar;
    RecyclerView mRecyclerView;

    private LoadingIndicator(ViewGroup layout) {
        mLayout = layout;
    }

    protected void onStatusChanged(State state) {
        try {
            if (State.IDLE.equals(state)) {
                mProgressbar.setVisibility(View.GONE);
            } else if (State.LOADING.equals(state)) {
                mProgressbar.setVisibility(View.VISIBLE);
            } else if (State.NONE.equals(state)) {
                mProgressbar.setVisibility(View.GONE);
            }
            if (mOnStatusChanged != null) {
                mOnStatusChanged.update(this, state);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LoadingIndicator with(OnLastItemVisible onLastItemVisible) {
        mLastItemVisible = onLastItemVisible;
        return this;
    }

    public LoadingIndicator with(OnStatusChanged onStatusChanged) {
        mOnStatusChanged = onStatusChanged;
        return this;
    }

    public static LoadingIndicator fromXml(Context context, @LayoutRes int id) {
        LoadingIndicator indicator = new LoadingIndicator((ViewGroup) View.inflate(context, id, null));
        try {
            indicator.mProgressbar = (ProgressBar) indicator.layout().findViewById(R.id.progressBar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return indicator;
    }

    public LoadingIndicator inject(RecyclerView recyclerView) {
        ViewParent viewParent = recyclerView.getParent();
        if (viewParent instanceof ViewGroup) {
            final int recyclerIndex = ((ViewGroup) viewParent).indexOfChild(recyclerView);

            FrameLayout newRootParent = new FrameLayout(recyclerView.getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(recyclerView.getLayoutParams());
            newRootParent.setLayoutParams(params);

            ((ViewGroup) viewParent).removeViewAt(recyclerIndex);
            recyclerView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            newRootParent.addView(recyclerView);
            FrameLayout.LayoutParams loadingParams = new FrameLayout.LayoutParams(ViewGroup
                    .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            loadingParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            layout().setLayoutParams(loadingParams);
            newRootParent.addView(layout());
            ((ViewGroup) viewParent).addView(newRootParent, recyclerIndex);
            layout().setVisibility(View.INVISIBLE);
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mCurrentScroll += dy;
                //TODO 可以优化位移
//                setTranslationY(-dy);
                int loadPos = recyclerView.getAdapter().getItemCount() - 1;
                if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                    ((StaggeredGridLayoutManager) recyclerView.getLayoutManager())
                            .findLastCompletelyVisibleItemPositions(mPositions);
                    if (dy > 0 && (mPositions[0] >= loadPos || mPositions[1] >= loadPos)) {
                        layout().setVisibility(View.VISIBLE);
                        updateFooterStatus();
                    } else {
                        layout().setVisibility(View.GONE);
                    }
                } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    if (dy > 0 && ((LinearLayoutManager) recyclerView.getLayoutManager())
                            .findLastCompletelyVisibleItemPosition() >= loadPos && recyclerView.getChildCount() > 0) {
                        layout().setVisibility(View.VISIBLE);
                        updateFooterStatus();
                    } else {
                        layout().setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        int spanCount;
        if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) recyclerView.getLayoutManager()).getSpanCount();
        } else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) recyclerView.getLayoutManager()).getSpanCount();
        } else {
            spanCount = 1;
        }
        recyclerView.addItemDecoration(new FooterDecoration(this, spanCount));
        mRecyclerView = recyclerView;
        return this;
    }

    private void updateFooterStatus() {
        if (mLastItemVisible != null) {
            mLastItemVisible.onVisible();
            if (State.IDLE.equals(mState)) {
                mLastItemVisible.onLoad(mState);
                mState = State.LOADING;
            }
            onStatusChanged(mState);
        }
    }

    public void setLoading(State state) {
        if (!state.equals(mState)) {
            mState = state;
            onStatusChanged(mState);
        }
    }

    static class FooterDecoration extends RecyclerView.ItemDecoration {
        LoadingIndicator mLoadingStatus;
        int mSpanCount;

        public FooterDecoration(LoadingIndicator loadingStatus, int spanCount) {
            mLoadingStatus = loadingStatus;
            mSpanCount = spanCount;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int count = parent.getAdapter().getItemCount();
            int position = parent.getChildAdapterPosition(view);
            if (count - mSpanCount <= position) {
                Log.d("Test Log", "spancount:" + mSpanCount + ",position:" + position);
                outRect.bottom = mLoadingStatus.layout().getHeight();
            }
        }
    }

    public ViewGroup layout() {
        return mLayout;
    }

    public enum State {
        IDLE, LOADING, NONE
    }

    public interface OnLastItemVisible {
        void onVisible();

        void onLoad(LoadingIndicator.State state);
    }

    public interface OnStatusChanged {
        void update(LoadingIndicator indicator, State state);
    }
}
