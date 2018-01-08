package com.mojka.poisk.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.contract.ForgotPasswordContract;
import com.mojka.poisk.ui.presenter.ForgotPasswordPresenterImpl;
import com.rey.material.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity implements ForgotPasswordContract.View {
    public static final String TAG = "ForgotPasswordActivity";

    private ForgotPasswordContract.Presenter presenter = new ForgotPasswordPresenterImpl();

    @BindView(R.id.btn_save)
    public Button btnSave;

    @BindView(R.id.btn_get_code)
    public Button btnGetCode;

    @BindView(R.id.et_password)
    public EditText etPassword;

    @BindView(R.id.et_phone)
    public EditText etPhone;

    @BindView(R.id.et_code)
    public EditText etCode;

    /*
     * Activity implementation
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.setView(this);
        presenter.start();
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    String getActivityTitle() {
        return getString(R.string.activity_forgot_password);
    }

    @Override
    OnCloseButtonListener getOnCloseButtonListener() {
        return this::finish;
    }

    /*
     * View implementation
     */

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public Activity getViewActivity() {
        return this;
    }

    @Override
    public void setupUi() {

    }

    @Override
    public void showSaveButton() {
        btnSave.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSaveButton() {
        btnSave.setVisibility(View.GONE);
    }

    @Override
    public void showGetCodeButton() {
        btnGetCode.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideGetCodeButton() {
        btnGetCode.setVisibility(View.GONE);
    }

    @Override
    @OnClick(R.id.btn_get_code)
    public void sendCode() {
        presenter.sendCode();
    }

    @Override
    @OnClick(R.id.btn_save)
    public void save() {
        presenter.verifyCode();
    }

    @Override
    public void showEtPassword() {
        etPassword.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEtPassword() {
        etPassword.setVisibility(View.GONE);
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }

    @Override
    public String getPhone() {
        return etPhone.getText().toString();
    }

    @Override
    public void setEtPhoneEditable(Boolean editable) {
        etPhone.setEnabled(editable);
    }

    @Override
    public String getCode() {
        return etCode.getText().toString();
    }
}
