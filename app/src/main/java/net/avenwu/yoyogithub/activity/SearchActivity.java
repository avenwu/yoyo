package net.avenwu.yoyogithub.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import net.avenwu.yoyogithub.R;
import net.avenwu.yoyogithub.databinding.ActivitySearchBinding;
import net.avenwu.yoyogithub.fragment.FragmentSearchRepoList;
import net.avenwu.yoyogithub.fragment.FragmentSearchUserList;

/**
 * Created by aven on 4/17/16.
 */
public class SearchActivity extends BaseActivity {
    private SearchView mSearchView;
    private String mQueryText;
    private SearchOptionItemClickListener mSearchListener = new SearchOptionItemClickListener() {
        @Override
        public void onClickRepo(View view) {
            invokeSearch(FragmentSearchRepoList.newInstance(mQueryText));
        }

        @Override
        public void onClickUser(View view) {
            invokeSearch(FragmentSearchUserList.newInstance(mQueryText));
        }
    };

    private void invokeSearch(Fragment fragment) {
        if (TextUtils.isEmpty(mQueryText)) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchView.getFocusedChild().getWindowToken(), 0);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.search_layout, fragment)
                .addToBackStack("search")
                .commitAllowingStateLoss();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.setListener(mSearchListener);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowCustomEnabled(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.search_layout, FragmentSearchRepoList.newInstance(query))
                        .addToBackStack("search")
                        .commitAllowingStateLoss();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mQueryText = newText;
                return true;
            }
        });
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                if (item.getItemId() == R.id.action_search) {
                    mSearchView.setQueryHint("Repositories/users/issues");
                }
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        });

        mSearchView.setIconifiedByDefault(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public interface SearchOptionItemClickListener {
        void onClickRepo(View view);

        void onClickUser(View view);
    }
}
