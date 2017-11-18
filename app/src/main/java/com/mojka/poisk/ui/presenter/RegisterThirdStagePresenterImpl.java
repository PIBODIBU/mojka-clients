package com.mojka.poisk.ui.presenter;

import android.text.TextUtils;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.contract.RegisterContract;

import java.util.LinkedList;
import java.util.List;

public class RegisterThirdStagePresenterImpl implements RegisterContract.ThirdStage.Presenter {
    private RegisterContract.ThirdStage.View view;
    private LinkedList<RegisterThirdStagePresenterImpl.AuthCallback> authCallbacks = new LinkedList<>();

    @Override
    public void start() {
        view.setupUi();
    }

    @Override
    public void setView(RegisterContract.ThirdStage.View view) {
        this.view = view;
    }

    @Override
    public void addAuthCallback(RegisterThirdStagePresenterImpl.AuthCallback authCallback) {
        authCallbacks.add(authCallback);
    }

    @Override
    public List<RegisterThirdStagePresenterImpl.AuthCallback> getAuthCallbacks() {
        return authCallbacks;
    }

    @Override
    public void register(String password, String passwordRepeat) {
        for (AuthCallback authCallback : getAuthCallbacks())
            authCallback.onStart();

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordRepeat)) {
            view.setErrorText(view.getViewActivity().getString(R.string.error_empty_password));
            return;
        }

        if (!password.equals(passwordRepeat)) {
            view.setErrorText(view.getViewActivity().getString(R.string.error_passwords_not_match));
            return;
        }
        ;

        for (AuthCallback authCallback : getAuthCallbacks())
            authCallback.onSuccess(password);
    }

    public static abstract class AuthCallback {
        public void onStart() {
        }

        public void onSuccess(String password) {
        }

        public void onError() {
        }
    }
}
