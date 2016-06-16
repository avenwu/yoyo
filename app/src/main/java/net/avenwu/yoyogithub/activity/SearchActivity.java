package net.avenwu.yoyogithub.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.avenwu.yoyogithub.R;
import net.avenwu.yoyogithub.databinding.ActivitySearchBinding;
import net.avenwu.yoyogithub.fragment.FragmentSearchRepoList;
import net.avenwu.yoyogithub.widget.EmptyLayout;

/**
 * Created by aven on 4/17/16.
 */
public class SearchActivity extends BaseActivity {
    private EditText mSearchEditText;
    private View mClearBtn;
    private SearchOptionItemClickListener mSearchListener = new SearchOptionItemClickListener() {
        @Override
        public void onClickRepo(View view) {
            String keyword = mSearchEditText.getText().toString();
            if (TextUtils.isEmpty(keyword)) {
                return;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.search_layout, FragmentSearchRepoList.newInstance(keyword))
                    .commitAllowingStateLoss();
        }

        @Override
        public void onClickIssue(View view) {

        }

        @Override
        public void onClickUser(View view) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.setListener(mSearchListener);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setTitle("");
            final View searchLayout = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(
                    R.layout.seach_title_layout, binding.toolbar, false);
            mSearchEditText = (EditText) searchLayout.findViewById(R.id.search_edit_text);
            mSearchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    mClearBtn.setVisibility(s.toString().trim().isEmpty() ? View.INVISIBLE : View.VISIBLE);
                }
            });
            mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (KeyEvent.KEYCODE_SEARCH == keyEvent.getKeyCode()) {
                        mSearchListener.onClickRepo(null);
                    }
                    return false;
                }
            });
            mClearBtn = searchLayout.findViewById(R.id.btn_clear);
            mClearBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSearchEditText != null) {
                        mSearchEditText.setText("");
                    }
                }
            });
            mClearBtn.setVisibility(View.INVISIBLE);
            getSupportActionBar().setCustomView(searchLayout, new ActionBar.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        }
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.emptyLayout.setType(EmptyLayout.HIDE);
        binding.toolbar.post(new Runnable() {
            @Override
            public void run() {
                if (mSearchEditText != null) {
                    mSearchEditText.requestFocus();
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(
                            mSearchEditText, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });

    }

    public interface SearchOptionItemClickListener {
        void onClickRepo(View view);

        void onClickIssue(View view);

        void onClickUser(View view);
    }
}
