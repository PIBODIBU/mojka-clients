package com.mojka.poisk.ui.contract;

import com.google.firebase.auth.PhoneAuthProvider;
import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;
import com.mojka.poisk.ui.presenter.RegisterSecondStagePresenterImpl;

public class RegisterContract {
    public interface View extends BaseView {
        void showFirstStage();

        void showSecondStage();

        PhoneAuthProvider.OnVerificationStateChangedCallbacks getOnVerificationStateChangedCallbacks();
    }

    public interface Presenter extends BasePresenter<View> {
    }

    public interface FirstStage {
        interface View extends BaseView {
            void next();

            void showProgressBar();

            void hideButton();

            Presenter getPresenter();
        }

        interface Presenter extends BasePresenter<RegisterContract.FirstStage.View> {
            void verifyPhoneNumber(String phoneNumber);

            String getPhoneNumber();

            void setOnVerificationStateChangedCallbacks(PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks);

            PhoneAuthProvider.OnVerificationStateChangedCallbacks getOnVerificationStateChangedCallbacks();

            int getAuthTimeDuration();
        }
    }

    public interface SecondStage {
        interface View extends BaseView {
            void finishRegistration();

            Presenter getPresenter();

            void showProgressBar();

            void hideProgressBar();

            void showButton();

            void hideButton();
        }

        interface Presenter extends BasePresenter<RegisterContract.SecondStage.View> {
            void signInWithCode(String code);

            void setVerificationId(String verificationId);

            String getVerificationId();

            void setAuthCallback(RegisterSecondStagePresenterImpl.AuthCallback authCallback);

            RegisterSecondStagePresenterImpl.AuthCallback getAuthCallback();
        }
    }
}
