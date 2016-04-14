package net.avenwu.yoyogithub.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import net.avenwu.yoyogithub.R;


/**
 * Provide UI set for default list-page, can be integrated into Activity/Fragment/View：<br>
 * 1. RecyclerView;<br>
 * 2. BaseEmptyLayout[optional];<br>
 * 3. SwipeRefreshLayout[optional];<br>
 * 4. Load more[optional]<br>
 * <p/>
 * Keep in mind {@link RecyclerViewDecorator} only provide simple UI elements,
 * which means it has nothing to with API request or Data handling, you need do all these stuff
 * manually according to different cases;
 * <p/>
 * Example:
 * <pre>{@code
 *      RecyclerViewDecorator decorator = new RecyclerViewDecorator.Builder(new Callback() {
 *                      @Override
 *          public RecyclerView.Adapter onCreateAdapter() {
 *              return new XXXAdapter();
 *          }
 *
 *          @Override
 *          public void onViewPrepared(RecyclerViewDecorator helper) {
 *              // custom configurations
 *              // TODO fetch data list
 *              // mPage = 0;
 *              // helper.stopRefreshing();
 *          }
 *
 *          @Override
 *          public void onRefresh(SwipeRefreshLayout swipeRefreshLayout) {
 *              // mPage = 0;
 *              // API request
 *          }
 *
 *          @Override
 *          public void onLoadMore() {
 *              // mPage++;
 *              //TODO load more reuqest
 *          }
 *      }).withRefresh(true).withLoadMore(true).withEmptyLayout(true).create(this);
 *
 *      ViewGroup layout = decorator.getView();
 * }
 *
 * Created by aven on 3/11/16.
 */
public class RecyclerViewDecorator {

    private ViewGroup mContainerLayout;
    private PolishedRecyclerView mRecyclerView;
    private EmptyLayout mEmptyLayout;
    @Nullable
    private SwipeRefreshLayout mSwipeLayout;
    @Nullable
    private LoadingIndicator mLoadingIndicator;
    private Callback mCallback;
    private boolean loadMoreEnable;

    public static class Builder {

        private Callback callback;
        private boolean refreshEnable;
        private boolean loadMoreEnable;
        private boolean needEmptyLayout;

        public Builder(Callback callback) {
            this.callback = callback;
        }

        public Builder withRefresh(boolean refreshEnable) {
            this.refreshEnable = refreshEnable;
            return this;
        }

        public Builder withLoadMore(boolean loadMoreEnable) {
            this.loadMoreEnable = loadMoreEnable;
            return this;
        }

        public Builder withEmptyLayout(boolean needEmptyLayout) {
            this.needEmptyLayout = needEmptyLayout;
            return this;
        }

        private void createInternal(Context context, RecyclerViewDecorator helper) {
            ViewGroup parent;
            if (refreshEnable) {
                helper.mSwipeLayout = new SwipeRefreshLayout(context);
                helper.mSwipeLayout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorPrimary));
                parent = helper.mSwipeLayout;
                parent.setLayoutParams(defaultLayoutParams());
                helper.mContainerLayout.addView(parent);
            } else {
                parent = helper.mContainerLayout;
            }
            helper.mRecyclerView = new PolishedRecyclerView(context);
            helper.mRecyclerView.setLayoutParams(defaultLayoutParams());
            parent.addView(helper.mRecyclerView);
            helper.loadMoreEnable = loadMoreEnable;
            if (needEmptyLayout) {
                helper.mEmptyLayout = new EmptyLayout(context);
                helper.mEmptyLayout.setLayoutParams(defaultLayoutParams());
                helper.mContainerLayout.addView(helper.mEmptyLayout);
                helper.mRecyclerView.setEmptyView(helper.mEmptyLayout);
            }
            helper.onCreate(context);
        }

        /**
         * create UI elements
         * {@link #replace(RecyclerView)}
         *
         * @param context
         * @return
         */
        public RecyclerViewDecorator create(Context context) {
            RecyclerViewDecorator helper = new RecyclerViewDecorator(callback);
            helper.mContainerLayout = new FrameLayout(context);
            helper.mContainerLayout.setLayoutParams(defaultLayoutParams());
            createInternal(context, helper);
            return helper;
        }

        /**
         * replace the predefined RecyclerView
         * {@link #create(Context)}
         *
         * @param recyclerView target to be replaced
         * @return
         */
        public RecyclerViewDecorator replace(@Nullable RecyclerView recyclerView) {
            RecyclerViewDecorator helper = new RecyclerViewDecorator(callback);
            if (recyclerView == null || recyclerView.getParent() == null) {
                throw new NullPointerException("Can not replace a null RecyclerView or RecyclerView without parent layout");
            }
            helper.mContainerLayout = (ViewGroup) recyclerView.getParent();
            helper.mContainerLayout.removeView(recyclerView);
            createInternal(recyclerView.getContext(), helper);
            return helper;
        }

        private ViewGroup.LayoutParams defaultLayoutParams() {
            return new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    public interface Callback {

        /**
         * Provide RecyclerView Adapter
         *
         * @return
         */
        RecyclerView.Adapter onCreateAdapter();

        /**
         * invoke after all the UI stuff prepared, the RecyclerView is set with LinearLayoutManager,
         * any custom configurations can be set manually after {@link #onViewPrepared(RecyclerViewDecorator)};
         *
         * @param helper
         */
        void onViewPrepared(RecyclerViewDecorator helper);

        /**
         * swipe callback of SwipeRefreshLayout
         *
         * @param swipeRefreshLayout
         */
        void onRefresh(SwipeRefreshLayout swipeRefreshLayout);

        /**
         * callback of LoadingIndicator
         */
        void onLoadMore();

    }

    private RecyclerViewDecorator(Callback callback) {
        mCallback = callback;
    }

    /**
     * ViewGroup which contains the RecyclerView and any other optional widgets
     *
     * @return Layout
     */
    public View getView() {
        return mContainerLayout;
    }


    public <T> T adapter(Class<T> clz) {
        return (T) getRecyclerView().getAdapter();
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Nullable
    public EmptyLayout getEmptyLayout() {
        return mEmptyLayout;
    }

    @Nullable
    public SwipeRefreshLayout getSwipeLayout() {
        return mSwipeLayout;
    }

    @Nullable
    public LoadingIndicator getLoadingIndicator() {
        return mLoadingIndicator;
    }

    public void stopRefreshing() {
        if (mSwipeLayout != null) {
            mSwipeLayout.setRefreshing(false);
        }
        if (mEmptyLayout != null) {
            mEmptyLayout.setType(EmptyLayout.HIDE);
        }
        if (mLoadingIndicator != null) {
            mLoadingIndicator.setLoading(LoadingIndicator.State.IDLE);
        }
    }

    public void startLoading() {
        if (mEmptyLayout != null) {
            mEmptyLayout.setType(EmptyLayout.LOADING);
        }
    }

    protected void onCreate(Context context) {
        if (mSwipeLayout != null) {
            mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (mCallback != null) {
                        mCallback.onRefresh(mSwipeLayout);
                    }
                }
            });
        }
        if (loadMoreEnable) {
            mLoadingIndicator = LoadingIndicator.fromXml(context, R.layout.loading_layout).inject(mRecyclerView).with(
                    new LoadingIndicator.OnLastItemVisible() {
                        @Override
                        public void onVisible() {

                        }

                        @Override
                        public void onLoad(LoadingIndicator.State state) {
                            if (mCallback != null) {
                                mCallback.onLoadMore();
                            }
                        }
                    });
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, OrientationHelper.VERTICAL, false));
        mRecyclerView.setAdapter(mCallback.onCreateAdapter());
        mCallback.onViewPrepared(this);
    }


    /**
     * add item divide to RecyclerView instacne
     *
     * @param recyclerView target instance
     * @param height       divider height(dp)
     */
    public static void setVerticalLine(RecyclerView recyclerView, float height) {
        recyclerView.addItemDecoration(new DividerDecoration(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height,
                        recyclerView.getContext().getResources().getDisplayMetrics())));
    }

    static class DividerDecoration extends RecyclerView.ItemDecoration {
        int mDividerHeight;
        Rect mDividerRect = new Rect();
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        public DividerDecoration(int height) {
            mDividerHeight = height;
            mPaint.setColor(0xFFEEEEEE);
            mPaint.setStyle(Paint.Style.FILL);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = mDividerHeight;
        }

        /**
         * draw our divider with specific so we don't need to set background of the RecyclerView instance
         *
         * @param c
         * @param parent
         * @param state
         */
        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDividerHeight;
                mDividerRect.set(left, top, right, bottom);
                c.drawRect(mDividerRect, mPaint);
            }
        }
    }

    public static class PolishedRecyclerView extends RecyclerView {
        private EmptyLayout mEmptyLayout;

        final private AdapterDataObserver observer = new AdapterDataObserver() {
            @Override
            public void onChanged() {
                checkIfEmpty();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                checkIfEmpty();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                checkIfEmpty();
            }
        };

        public PolishedRecyclerView(Context context) {
            super(context);
        }

        public PolishedRecyclerView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        void checkIfEmpty() {
            if (mEmptyLayout != null) {
                final boolean emptyViewVisible = getAdapter() == null || getAdapter().getItemCount() == 0;
                if (emptyViewVisible) {
                    mEmptyLayout.setType(EmptyLayout.EMPTY);
                    setVisibility(GONE);
                } else {
                    mEmptyLayout.setType(EmptyLayout.HIDE);
                    setVisibility(VISIBLE);
                }
            }
        }

        @Override
        public void setAdapter(Adapter adapter) {
            final Adapter oldAdapter = getAdapter();
            if (oldAdapter != null) {
                oldAdapter.unregisterAdapterDataObserver(observer);
            }
            super.setAdapter(adapter);
            if (adapter != null) {
                adapter.registerAdapterDataObserver(observer);
            }

            checkIfEmpty();
        }

        public void setEmptyView(EmptyLayout mEmptyLayout) {
            this.mEmptyLayout = mEmptyLayout;
            checkIfEmpty();
        }
    }
}
