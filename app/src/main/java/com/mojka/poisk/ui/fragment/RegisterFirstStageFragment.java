package com.mojka.poisk.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
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
        presenter.setOnVerificationStateChangedCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                tvError.setText(getViewActivity().getString(R.string.error_auth_phone));
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                getViewActivity().getOnVerificationStateChangedCallbacks().onCodeSent(verificationId, forceResendingToken);
            }
        });
        presenter.setView(this);

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
    public void hideButton() {
        btnNext.setClickable(false);
        btnNext.setText("");
    }

    @Override
    public RegisterContract.FirstStage.Presenter getPresenter() {
        return presenter;
    }
}