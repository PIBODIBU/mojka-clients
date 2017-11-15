package com.mojka.poisk.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.contract.LoginContract;
import com.mojka.poisk.ui.presenter.LoginPresenterImpl;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.View {
    @BindView(R.id.ivBackground)
    public ImageView ivBackground;

    private LoginContract.Presenter presenter = new LoginPresenterImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.setView(this);
        setupUi();
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public AppCompatActivity getViewActivity() {
        return this;
    }

    @Override
    public void setupUi() {
        loadBackground();
    }

    @Override
    @OnClick(R.id.btn_register)
    public void register() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    @Override
    public void loadBackground() {
        Picasso.with(this)
                .load(R.drawable.img_bg_login)
                .into(ivBackground);
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    String getActivityTitle() {
        return getString(R.string.activity_login_title);
    }

    @Override
    OnCloseButtonListener getOnCloseButtonListener() {
        return new OnCloseButtonListener() {
            @Override
            public void onclick() {
                finish();
            }
        };
    }

    @OnClick(R.id.skip)
    public void skip() {
        startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
    }
}
