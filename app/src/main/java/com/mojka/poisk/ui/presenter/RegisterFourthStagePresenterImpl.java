package com.mojka.poisk.ui.presenter;

import com.mojka.poisk.ui.contract.RegisterContract;

import java.util.LinkedList;
import java.util.List;

public class RegisterFourthStagePresenterImpl implements RegisterContract.FourthStage.Presenter {
    private RegisterContract.FourthStage.View view;
    private LinkedList<AuthCallback> authCallbacks = new LinkedList<>();

    @Override
    public void start() {
        view.setupUi();
    }

    @Override
    public void addAuthCallback(AuthCallback authCallback) {
        if (authCallback != null)
            authCallbacks.add(authCallback);
    }

    @Override
    public List<AuthCallback> getAuthCallbacks() {
        return authCallbacks;
    }

    @Override
    public void register() {
        for (AuthCallback authCallback : getAuthCallbacks())
            authCallback.onSuccess();
    }

    @Override
    public void skip() {
        for (AuthCallback authCallback : getAuthCallbacks())
            authCallback.onSkip();
    }

    @Override
    public void setView(RegisterContract.FourthStage.View view) {
        this.view = view;
    }

    public static abstract class AuthCallback {
        public void onStart() {
        }

        public void onSuccess() {
        }

        public void onSkip() {
        }

        public void onError() {
        }
    }
}
