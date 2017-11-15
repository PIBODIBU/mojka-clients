package com.mojka.poisk.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mojka.poisk.R;
import com.mojka.poisk.ui.contract.RegisterContract;
import com.mojka.poisk.ui.fragment.RegisterFirstStageFragment;
import com.mojka.poisk.ui.fragment.RegisterSecondStageFragment;
import com.mojka.poisk.ui.presenter.RegisterPresenterImpl;

public class RegisterActivity extends BaseActivity implements RegisterContract.View {
    private final String TAG = "RegisterActivity";

    private RegisterContract.Presenter presenter = new RegisterPresenterImpl();
    private RegisterFirstStageFragment firstStageFragment = new RegisterFirstStageFragment();
    private RegisterSecondStageFragment secondStageFragment = new RegisterSecondStageFragment();

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
        }

        @Override
        public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            secondStageFragment.getPresenter().setVerificationId(verificationId);
            showSecondStage();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.setView(this);
        showFirstStage();
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
    public AppCompatActivity getViewActivity() {
        return this;
    }

    @Override
    public void setupUi() {
    }

    @Override
    public PhoneAuthProvider.OnVerificationStateChangedCallbacks getOnVerificationStateChangedCallbacks() {
        return callbacks;
    }

    @Override
    public void showFirstStage() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, firstStageFragment)
                .commit();
    }

    @Override
    public void showSecondStage() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, secondStageFragment)
                .commit();
    }
}
