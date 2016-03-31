package net.avenwu.yoyogithub.widget;

import android.content.Context;
import android.database.Observable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.avenwu.yoyogithub.R;


public class EmptyLayout extends FrameLayout implements android.view.View.OnClickListener {

    public static final int HIDE = 1;
    public static final int LOADING = 2;
    public static final int EMPTY = 3;

    private ProgressBar mProgressBar;
    private boolean clickEnable = true;
    public ImageView mImage;
    private android.view.View.OnClickListener listener;
    private int mErrorState;
    private TextView mLabel;
    LayoutStatusObservable mObservable = new LayoutStatusObservable();

    public EmptyLayout(Context context) {
        this(context, null);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        View.inflate(context, R.layout.empty_layout, this);
        mImage = (ImageView) findViewById(R.id.iv_icon);
        mLabel = (TextView) findViewById(R.id.tv_label);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        setOnClickListener(this);
        mImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (clickEnable) {
                    if (listener != null)
                        listener.onClick(v);
                }
            }
        });
    }

    public void dismiss() {
        mErrorState = HIDE;
        setVisibility(View.GONE);
    }

    public int getErrorState() {
        return mErrorState;
    }

    public boolean isLoading() {
        return mErrorState == LOADING;
    }

    @Override
    public void onClick(View v) {
        if (clickEnable) {
            if (listener != null)
                listener.onClick(v);
        }
    }

    public void setErrorMessage(String msg) {
        mLabel.setText(msg);
    }

    public void setErrorMessage(@StringRes int resId) {
        mLabel.setText(resId);
    }

    public void setErrorImag(int imgResource) {
        try {
            mImage.setImageResource(imgResource);
        } catch (Exception e) {
        }
    }

    public void setType(int i) {
        setVisibility(View.VISIBLE);
        switch (i) {
            case LOADING:
                mErrorState = LOADING;
                mProgressBar.setVisibility(View.VISIBLE);
                mImage.setVisibility(View.GONE);
                mLabel.setText("Loading...");
                clickEnable = false;
                break;
            case EMPTY:
                mErrorState = EMPTY;
//                mImage.setBackgroundResource(R.drawable.ic_none_data);
                mImage.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                setTvNoDataContent();
                clickEnable = true;
                break;
            case HIDE:
                setVisibility(View.GONE);
                break;
            default:
                break;
        }
        mObservable.notifyChanged(this, i);
    }

    public void setOnLayoutClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setTvNoDataContent() {
        mLabel.setText("No data found!");
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.GONE)
            mErrorState = HIDE;
        super.setVisibility(visibility);
    }

    public void registerStatusObserver(StatusObserver observer) {
        mObservable.registerObserver(observer);
    }

    public void unregisterStatusObserver(StatusObserver observer) {
        mObservable.unregisterObserver(observer);
    }

    /**
     * Observe the EmptyView status, change UI such as text, image
     */
    public interface StatusObserver {
        void onChanged(EmptyLayout layout, int type);
    }

    static class LayoutStatusObservable extends Observable<StatusObserver> {
        public void notifyChanged(EmptyLayout layout, int type) {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onChanged(layout, type);
            }
        }
    }
}
