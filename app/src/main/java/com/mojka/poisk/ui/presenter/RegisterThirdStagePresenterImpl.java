package com.mojka.poisk.ui.presenter;

import com.mojka.poisk.ui.contract.RegisterContract;

import java.util.LinkedList;
import java.util.List;

public class RegisterThirdStagePresenterImpl implements RegisterContract.ThirdStage.Presenter {
    private RegisterContract.ThirdStage.View view;
    private LinkedList<AuthCallback> authCallbacks = new LinkedList<>();

    @Override
    public void start() {

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
    public void setView(RegisterContract.ThirdStage.View view) {
        this.view = view;
    }

    public static abstract class AuthCallback {
        public void onStart() {
        }

        public void onSuccess(String verificationId) {
        }

        public void onError() {
        }
    }
}
