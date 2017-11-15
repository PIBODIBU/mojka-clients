package com.mojka.poisk.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.EditText;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.contract.RegisterContract;
import com.mojka.poisk.ui.presenter.RegisterPresenterImpl;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements RegisterContract.View {
    @BindView(R.id.et_name)
    public EditText etName;

    @BindView(R.id.et_phone)
    public EditText etPhone;

    private RegisterContract.Presenter presenter = new RegisterPresenterImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.setView(this);
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    String getActivityTitle() {
        return "Регистрация";
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

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void setupUi() {
        etPhone.setText(presenter.getPhoneNumber());
    }

    @Override
    @OnClick(R.id.btn_next)
    public void next() {
        presenter.verifyPhoneNumber(etPhone.getText().toString());
    }
}
