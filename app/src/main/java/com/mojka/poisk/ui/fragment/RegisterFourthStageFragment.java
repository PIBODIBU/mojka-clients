package com.mojka.poisk.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.activity.RegisterActivity;
import com.mojka.poisk.ui.contract.RegisterContract;
import com.mojka.poisk.ui.presenter.RegisterFourthStagePresenterImpl;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterFourthStageFragment extends BaseFragment implements RegisterContract.FourthStage.View {
    @BindView(R.id.progress_view)
    public ProgressView progressView;

    @BindView(R.id.btn_continue)
    public Button btnContinue;

    @BindView(R.id.et_city)
    public EditText etCity;

    @BindView(R.id.et_car)
    public EditText etCar;

    @BindView(R.id.tv_error)
    public TextView tvError;

    private RegisterContract.FourthStage.Presenter presenter = new RegisterFourthStagePresenterImpl();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        presenter.setView(this);
        presenter.start();

        super.onCreate(savedInstanceState);
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

    }

    @Override
    public void showToast(int text) {
        Toast.makeText(getViewContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getViewContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    @OnClick(R.id.btn_continue)
    public void register() {
        presenter.register(etCity.getText().toString(), etCar.getText().toString());
    }

    @Override
    @OnClick(R.id.btn_skip)
    public void skip() {
        presenter.skip();
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_register_fourth_stage;
    }

    @Override
    public RegisterContract.FourthStage.Presenter getPresenter() {
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
        btnContinue.setClickable(true);
    }

    @Override
    public void hideButton() {
        btnContinue.setClickable(false);
    }

    @Override
    public void setErrorText(String text) {
        this.tvError.setText(text);
    }
}
