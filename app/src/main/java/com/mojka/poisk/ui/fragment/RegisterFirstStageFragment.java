package com.mojka.poisk.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.activity.RegisterActivity;
import com.mojka.poisk.ui.contract.RegisterContract;
import com.mojka.poisk.ui.presenter.RegisterFirstStagePresenterImpl;
import com.rey.material.widget.ProgressView;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterFirstStageFragment extends BaseFragment implements RegisterContract.FirstStage.View {
    @BindView(R.id.et_name)
    public EditText etName;

    @BindView(R.id.et_phone)
    public EditText etPhone;

    @BindView(R.id.btn_next)
    public Button btnNext;

    @BindView(R.id.tv_error)
    public TextView tvError;

    @BindView(R.id.progress_view)
    public ProgressView progressView;

    private RegisterContract.FirstStage.Presenter presenter = new RegisterFirstStagePresenterImpl();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter.setView(this);
        presenter.start();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_register_first_stage;
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public RegisterActivity getViewActivity() {
        return ((RegisterActivity) getActivity());
    }

    @Override
    public void setupUi() {
        etPhone.setText(presenter.getPhoneNumber());
    }

    @Override
    @OnClick(R.id.btn_next)
    public void next() {
        String phoneNumber = etPhone.getText().toString();

        if (phoneNumber.equals("")) {
            Toast.makeText(getViewActivity(), R.string.toast_empty_phone, Toast.LENGTH_SHORT).show();
            return;
        }

        hideButton();
        showProgressBar();
        presenter.verifyPhoneNumber(phoneNumber);
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
    public void showButton() {
        btnNext.setClickable(true);
    }

    @Override
    public void hideButton() {
        btnNext.setClickable(false);
    }

    @Override
    public void setErrorText(String text) {
        this.tvError.setText(text);
    }

    @Override
    public RegisterContract.FirstStage.Presenter getPresenter() {
        return presenter;
    }
}