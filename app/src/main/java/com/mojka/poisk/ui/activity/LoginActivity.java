package com.mojka.poisk.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.contract.LoginContract;
import com.mojka.poisk.ui.presenter.LoginPresenterImpl;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.View {
    public static final String KEY_FINISH_AFTER_SUCCESS = "KEY_FINISH_AFTER_SUCCESS";

    @BindView(R.id.ivBackground)
    public ImageView ivBackground;

    @BindView(R.id.et_phone)
    public EditText etPhone;

    @BindView(R.id.et_password)
    public EditText etPassword;

    @BindView(R.id.progress_view)
    public ProgressView progressView;

    @BindView(R.id.btn_login)
    public Button btnLogin;

    @BindView(R.id.btn_register)
    public Button btnRegister;

    private LoginContract.Presenter presenter = new LoginPresenterImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.setView(this);
        presenter.start();
    }

    @Override
    public void showToast(int text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startProfileActivity() {
        startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
        finish();
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
    public void startRegisterActivity() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    @Override
    public String getPhoneNumber() {
        return etPhone.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }

    @Override
    public void loadBackground() {
        Picasso.with(this)
                .load(R.drawable.img_bg_login)
                .into(ivBackground);
    }

    @Override
    @OnClick(R.id.btn_login)
    public void login() {
        presenter.login();
    }

    @Override
    public void showProgressBar() {
        progressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void freezeUI() {
        btnLogin.setClickable(false);
        btnRegister.setClickable(false);
    }

    @Override
    public void unfreezeUI() {
        btnLogin.setClickable(true);
        btnRegister.setClickable(true);
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
}
