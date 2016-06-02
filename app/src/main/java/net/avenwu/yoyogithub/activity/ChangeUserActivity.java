package net.avenwu.yoyogithub.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import net.avenwu.yoyogithub.R;
import net.avenwu.yoyogithub.bean.User;
import net.avenwu.yoyogithub.databinding.ActivityChangeUserBinding;
import net.avenwu.yoyogithub.presenter.ChangeAccountPresenter;
import net.avenwu.yoyogithub.presenter.Presenter;
import net.avenwu.yoyogithub.util.Preference;
import net.avenwu.yoyogithub.util.Tools;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Chaobin Wu on 6/1/16.
 */
public class ChangeUserActivity extends BaseActivity<ChangeAccountPresenter> {
    ActivityChangeUserBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean changeAccount = getIntent().getBooleanExtra("change_account", false);
        String localAccount = Preference.get(this).getUserAccountName();
        boolean hasNoAccount = TextUtils.isEmpty(localAccount);
        if (changeAccount || hasNoAccount) {
            mBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_user);
            setSupportActionBar(mBinding.toolbar);
            mBinding.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String name = mBinding.inputUserName.getText().toString().trim();
                    if (TextUtils.isEmpty(name)) {
                        Snackbar.make(mBinding.coordinatorLayout, "请输入GitHub账号", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    if (!Tools.isNetworkConnected(v.getContext())) {
                        Snackbar.make(mBinding.coordinatorLayout, "请检查请检查网络连接是否正常", Snackbar.LENGTH_SHORT).show();
                        return;
                    }

                    presenter(new Presenter.Action<ChangeAccountPresenter>() {
                        @Override
                        public void onRender(ChangeAccountPresenter data) {
                            mBinding.btnConfirm.setEnabled(false);
                            mBinding.inputLayout.setVisibility(View.GONE);
                            mBinding.loginProgress.setVisibility(View.VISIBLE);
                            mBinding.loginProgress.show();
                            data.checkAccount(name, new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    mBinding.inputLayout.setVisibility(View.VISIBLE);
                                    mBinding.btnConfirm.setEnabled(true);
                                    mBinding.loginProgress.hide();
                                    Preference.get(ChangeUserActivity.this).setUserAccountName(response.body().login);
                                    enter();
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    mBinding.inputLayout.setVisibility(View.VISIBLE);
                                    mBinding.btnConfirm.setEnabled(true);
                                    mBinding.loginProgress.hide();
                                    Snackbar.make(mBinding.coordinatorLayout, "请检查GitHub账号是否正确", Snackbar.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            });
        } else {
            enter();
        }
    }

    private void enter() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
