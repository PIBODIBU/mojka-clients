package com.mojka.poisk.ui.fragment;

import android.app.Activity;
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
import com.mojka.poisk.ui.contract.RegisterContract;
import com.mojka.poisk.ui.presenter.RegisterSecondStagePresenterImpl;
import com.rey.material.widget.ProgressView;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterSecondStageFragment extends BaseFragment implements RegisterContract.SecondStage.View {

    @BindView(R.id.et_code)
    public EditText etCode;

    @BindView(R.id.btn_finish)
    public Button btnFinish;

    @BindView(R.id.tv_error)
    public TextView tvError;

    @BindView(R.id.progress_view)
    public ProgressView progressView;

    private RegisterContract.SecondStage.Presenter presenter = new RegisterSecondStagePresenterImpl();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter.setView(this);
        presenter.start();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public Activity getViewActivity() {
        return getActivity();
    }

    @Override
    public void setupUi() {

    }

    @Override
    public void showToast(int text) {
        Toast.makeText(getViewActivity(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_register_second_stage;
    }

    @Override
    @OnClick(R.id.btn_finish)
    public void finishRegistration() {
        presenter.signInWithCode(etCode.getText().toString());
    }

    @Override
    public RegisterContract.SecondStage.Presenter getPresenter() {
        return presenter;
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
        btnFinish.setClickable(true);
    }

    @Override
    public void hideButton() {
        btnFinish.setClickable(false);
    }

    @Override
    public void setErrorText(String text) {
        this.tvError.setText(text);
    }
}
