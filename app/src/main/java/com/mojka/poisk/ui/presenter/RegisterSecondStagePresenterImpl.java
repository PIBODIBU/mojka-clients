package com.mojka.poisk.ui.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mojka.poisk.ui.contract.RegisterContract;

public class RegisterSecondStagePresenterImpl implements RegisterContract.SecondStage.Presenter {
    private final String TAG = "RegisterSecondPresenter";
    private RegisterContract.SecondStage.View view;

    private String verificationId;
    private AuthCallback authCallback;

    @Override
    public void start() {

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

        if (getAuthCallback() != null)
            getAuthCallback().onStart();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(getVerificationId(), code);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { // Sign in success
                            FirebaseUser user = task.getResult().getUser();

                            if (getAuthCallback() != null)
                                getAuthCallback().onSuccess();
                        } else { // Sign in failed
                            if (getAuthCallback() != null)
                                getAuthCallback().onError();

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)  // The verification code entered was invalid
                                if (getAuthCallback() != null)
                                    getAuthCallback().onInvalidCode();
                        }
                    }
                });
    }

    @Override
    public void setAuthCallback(AuthCallback authCallback) {
        this.authCallback = authCallback;
    }

    @Override
    public AuthCallback getAuthCallback() {
        return authCallback;
    }

    @Override
    public void setVerificationId(String verificationId) {
        this.verificationId = verificationId;
    }

    @Override
    public String getVerificationId() {
        return verificationId;
    }

    public interface AuthCallback {
        void onStart();

        void onSuccess();

        void onError();

        void onInvalidCode();
    }
}
