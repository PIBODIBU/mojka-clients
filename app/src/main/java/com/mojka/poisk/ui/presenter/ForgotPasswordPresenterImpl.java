package com.mojka.poisk.ui.presenter;

import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mojka.poisk.R;
import com.mojka.poisk.data.api.APIGenerator;
import com.mojka.poisk.data.api.inrerfaces.UserAPI;
import com.mojka.poisk.data.callback.Callback;
import com.mojka.poisk.data.model.BaseErrorResponse;
import com.mojka.poisk.ui.contract.ForgotPasswordContract;

import java.util.concurrent.TimeUnit;

public class ForgotPasswordPresenterImpl implements ForgotPasswordContract.Presenter {
    private static final String TAG = "ForgotPasswordPresenter";

    private ForgotPasswordContract.View view;

    private String verificationId;

    @Override
    public void start() {
        view.setupUi();

        view.showGetCodeButton();
        view.hideSaveButton();
        view.hideEtPassword();
        view.setEtPhoneEditable(true);
    }

    @Override
    public void setView(ForgotPasswordContract.View view) {
        this.view = view;
    }

    @Override
    public void save() {
        APIGenerator.createService(UserAPI.class).restorePassword(
                view.getPhone(),
                view.getPassword()).enqueue(new Callback<BaseErrorResponse>() {
            @Override
            public void onSuccess(BaseErrorResponse response) {
                if (response.getError()) {
                    onError();
                    return;
                }

                view.getViewActivity().getString(R.string.password_changed);
                view.getViewActivity().finish();
            }

            @Override
            public void onError() {
                Toast.makeText(view.getViewContext(), view.getViewActivity().getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void sendCode() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                view.getPhone(),    // Phone number to verify
                60, // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                view.getViewActivity(), // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        ForgotPasswordPresenterImpl.this.verificationId = verificationId;

                        view.setEtPhoneEditable(false);
                        view.showEtPassword();
                        view.hideGetCodeButton();
                        view.showSaveButton();
                    }
                });
    }

    @Override
    public void verifyCode() {
        FirebaseAuth.getInstance()
                .signInWithCredential(PhoneAuthProvider.getCredential(verificationId, view.getCode()))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) { // Sign in success
                        save();
                    } else { // Sign in failed
                        Toast.makeText(view.getViewContext(), view.getViewActivity().getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
