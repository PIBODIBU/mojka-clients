package com.mojka.poisk.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.contract.RegisterContract;
import com.mojka.poisk.ui.presenter.RegisterThirdStagePresenterImpl;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterThirdStageFragment extends BaseFragment implements RegisterContract.ThirdStage.View {

    @BindView(R.id.tv_error)
    public TextView tvError;

    @BindView(R.id.et_password)
    public EditText etPassword;

    @BindView(R.id.et_password_repeat)
    public EditText etPasswordRepeat;

    private RegisterContract.ThirdStage.Presenter presenter = new RegisterThirdStagePresenterImpl();

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter.setView(this);
        presenter.start();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Activity getViewActivity() {
        return getActivity();
    }

    @Override
    public void setupUi() {

    }

    @Override
    @OnClick(R.id.btn_register)
    public void register() {
        presenter.register(etPassword.getText().toString(), etPasswordRepeat.getText().toString());
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_register_third_stage;
    }

    @Override
    public RegisterContract.ThirdStage.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showButton() {

    }

    @Override
    public void hideButton() {

    }

    @Override
    public void setErrorText(String text) {
        tvError.setText(text);
    }
}
