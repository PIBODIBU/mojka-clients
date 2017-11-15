package com.mojka.poisk.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.contract.RegisterContract;
import com.mojka.poisk.ui.fragment.RegisterFirstStageFragment;
import com.mojka.poisk.ui.fragment.RegisterSecondStageFragment;
import com.mojka.poisk.ui.fragment.RegisterThirdStageFragment;
import com.mojka.poisk.ui.presenter.RegisterFirstStagePresenterImpl;
import com.mojka.poisk.ui.presenter.RegisterPresenterImpl;
import com.mojka.poisk.ui.presenter.RegisterSecondStagePresenterImpl;

public class RegisterActivity extends BaseActivity implements RegisterContract.View {
    private final String TAG = "RegisterActivity";

    private RegisterContract.Presenter presenter = new RegisterPresenterImpl();
    private RegisterFirstStageFragment firstStageFragment = new RegisterFirstStageFragment();
    private RegisterSecondStageFragment secondStageFragment = new RegisterSecondStageFragment();
    private RegisterThirdStageFragment thirdStageFragment = new RegisterThirdStageFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firstStageFragment.getPresenter().addAuthCallback(new RegisterFirstStagePresenterImpl.AuthCallback() {
            @Override
            public void onSuccess(String verificationId) {
                secondStageFragment.getPresenter().setVerificationId(verificationId);
                showSecondStage();
            }
        });

        secondStageFragment.getPresenter().addAuthCallback(new RegisterSecondStagePresenterImpl.AuthCallback() {
            @Override
            public void onSuccess() {
                showThirdStage();
            }
        });

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
    public void showFirstStage() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, firstStageFragment)
                .commit();
    }

    @Override
    public void showSecondStage() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0, 0)
                .replace(R.id.frame, secondStageFragment)
                .commit();
    }

    @Override
    public void showThirdStage() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0, 0)
                .replace(R.id.frame, thirdStageFragment)
                .commit();
    }
}
