package com.mojka.poisk.ui.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mojka.poisk.ui.contract.RegisterContract;

import java.util.concurrent.TimeUnit;

public class RegisterPresenterImpl implements RegisterContract.Presenter {
    private final String TAG = "RegisterPresenterImpl";

    private RegisterContract.View view;

    @Override
    public void start() {
    }

    @Override
    public void setView(RegisterContract.View view) {
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
                60, // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                view.getActivity(), // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Log.d(TAG, "onVerificationCompleted: " + e);
                    }
                });
    }
}
