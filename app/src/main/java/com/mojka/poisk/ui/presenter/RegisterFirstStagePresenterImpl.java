package com.mojka.poisk.ui.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.google.firebase.auth.PhoneAuthProvider;
import com.mojka.poisk.ui.contract.RegisterContract;

import java.util.concurrent.TimeUnit;

public class RegisterFirstStagePresenterImpl implements RegisterContract.FirstStage.Presenter {
    private final String TAG = "RegisterFirstPresenter";

    private RegisterContract.FirstStage.View view;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    @Override
    public void start() {
    }

    @Override
    public void setView(RegisterContract.FirstStage.View view) {
        this.view = view;
    }

    @SuppressLint("MissingPermission")
    @Override
    public String getPhoneNumber() {
        TelephonyManager tMgr = (TelephonyManager) view.getViewContext().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tMgr.getLine1Number();
    }

    @Override
    public void verifyPhoneNumber(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,    // Phone number to verify
                getAuthTimeDuration(), // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                view.getViewActivity(), // Activity (for callback binding)
                getOnVerificationStateChangedCallbacks());
    }

    @Override
    public int getAuthTimeDuration() {
        return 60;
    }

    @Override
    public void setOnVerificationStateChangedCallbacks(PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public PhoneAuthProvider.OnVerificationStateChangedCallbacks getOnVerificationStateChangedCallbacks() {
        return callbacks;
    }
}
