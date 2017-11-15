package com.mojka.poisk.ui.presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mojka.poisk.R;
import com.mojka.poisk.ui.contract.RegisterContract;

import java.util.LinkedList;

public class RegisterSecondStagePresenterImpl implements RegisterContract.SecondStage.Presenter {
    private final String TAG = "RegisterSecondPresenter";
    private RegisterContract.SecondStage.View view;

    private String verificationId;
    private LinkedList<AuthCallback> authCallbacks = new LinkedList<>();

    @Override
    public void start() {
        addAuthCallback(new RegisterSecondStagePresenterImpl.AuthCallback() {
            @Override
            public void onStart() {
                view.setErrorText("");
                view.hideButton();
                view.showProgressBar();
            }

            @Override
            public void onSuccess() {
                view.showButton();
                view.hideProgressBar();

                Toast.makeText(view.getViewActivity(), "Вы успешно зарегистрировались", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {
                view.setErrorText(view.getViewActivity().getString(R.string.error_auth_phone));
                view.showButton();
                view.hideProgressBar();
            }

            @Override
            public void onInvalidCode() {
                view.setErrorText(view.getViewActivity().getString(R.string.error_invalid_code));
                view.showButton();
                view.hideProgressBar();
            }
        });
    }

    @Override
    public void setView(RegisterContract.SecondStage.View view) {
        this.view = view;
    }

    @Override
    public void signInWithCode(String code) {
        if (verificationId == null) {
            Log.d(TAG, "signInWithCode: verification id is null");
            return;
        }

        if (code.equals("")) {
            view.showToast(R.string.toast_empty_code);
            return;
        }

        for (AuthCallback authCallback : authCallbacks)
            authCallback.onStart();

        FirebaseAuth.getInstance()
                .signInWithCredential(PhoneAuthProvider.getCredential(getVerificationId(), code))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { // Sign in success
                            FirebaseUser user = task.getResult().getUser();


                            for (AuthCallback authCallback : authCallbacks)
                                authCallback.onSuccess();
                        } else { // Sign in failed
                            for (AuthCallback authCallback : authCallbacks)
                                authCallback.onError();

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)  // The verification code entered was invalid
                                for (AuthCallback authCallback : authCallbacks)
                                    authCallback.onInvalidCode();
                        }
                    }
                });
    }

    @Override
    public void addAuthCallback(AuthCallback authCallback) {
        if (authCallback != null)
            authCallbacks.add(authCallback);
    }

    @Override
    public LinkedList<AuthCallback> getAuthCallbacks() {
        return authCallbacks;
    }

    @Override
    public void setVerificationId(String verificationId) {
        this.verificationId = verificationId;
    }

    @Override
    public String getVerificationId() {
        return verificationId;
    }

    public static abstract class AuthCallback {
        public void onStart() {
        }

        public void onSuccess() {
        }

        public void onError() {
        }

        public void onInvalidCode() {
        }
    }
}
