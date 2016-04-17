package net.avenwu.yoyogithub.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.avenwu.yoyogithub.R;
import net.avenwu.yoyogithub.databinding.NavHeaderMainBinding;
import net.avenwu.yoyogithub.fragment.ContributionFragment;
import net.avenwu.yoyogithub.fragment.FragmentRepoList;
import net.avenwu.yoyogithub.fragment.FragmentUserFeedList;
import net.avenwu.yoyogithub.fragment.FragmentUserList;
import net.avenwu.yoyogithub.bean.User;
import net.avenwu.yoyogithub.presenter.Presenter;
import net.avenwu.yoyogithub.presenter.ProfilePresenter;
import net.avenwu.yoyogithub.widget.EmptyLayout;

import java.lang.ref.WeakReference;

public class MainActivity extends BaseActivity<ProfilePresenter>
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    NavigationView navigationView;
    //TODO
    String userName = "avenwu";
    SparseArray<WeakReference<Fragment>> mFragments = new SparseArray<>(4);
    EmptyLayout mEmptyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEmptyLayout = (EmptyLayout) findViewById(R.id.empty_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
        presenter(new Presenter.Action<ProfilePresenter>() {
            @Override
            public void onRender(ProfilePresenter data) {
                data.addAction(Presenter.ACTION_1, new Presenter.Action<User>() {
                    @Override
                    public void onRender(User data) {
                        mEmptyLayout.setType(EmptyLayout.HIDE);
                        NavHeaderMainBinding binding = NavHeaderMainBinding.inflate((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE));
                        binding.setUser(data);
                        if (navigationView != null) {
                            navigationView.addHeaderView(binding.navHeaderContainer);
                        }
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        drawer.openDrawer(Gravity.START);
                    }
                }).addAction(Presenter.ACTION_2, new Presenter.Action<String>() {
                    @Override
                    public void onRender(String data) {
                        mEmptyLayout.setType(EmptyLayout.EMPTY);
                        mEmptyLayout.setErrorMessage(data);
                        mEmptyLayout.setOnLayoutClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                presenter(new Presenter.Action<ProfilePresenter>() {
                                    @Override
                                    public void onRender(ProfilePresenter data) {
                                        mEmptyLayout.setType(EmptyLayout.LOADING);
                                        data.fetchUserData(userName);
                                    }
                                });
                            }
                        });
                    }
                });
                mEmptyLayout.setType(EmptyLayout.LOADING);
                data.fetchUserData(userName);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        WeakReference<Fragment> reference = mFragments.get(id);
        Fragment fragment = null;
        if (reference == null || reference.get() == null) {
            switch (id) {
                case R.id.nav_contributions:
                    fragment = ContributionFragment.newInstance(userName);
                    break;
                case R.id.nav_repositories:
                    fragment = FragmentRepoList.newInstance(userName);
                    break;
                case R.id.nav_public_activity:
                    fragment = FragmentUserFeedList.newInstance(userName);
                    break;
                case R.id.nav_follower:
                    fragment = FragmentUserList.newInstance(userName, FragmentUserList.FOLLOWER);
                    break;
                case R.id.nav_following:
                    fragment = FragmentUserList.newInstance(userName, FragmentUserList.FOLLOWING);
                    break;
                case R.id.nav_search:
                    startActivity(new Intent(MainActivity.this, SearchActivity.class));
                    return true;
                case R.id.nav_share:

                    break;
                case R.id.nav_send:

                    break;
            }
            mFragments.put(id, new WeakReference<>(fragment));
        } else {
            fragment = reference.get();
        }
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_content, fragment)
                    .commitAllowingStateLoss();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
