package com.mojka.poisk.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.mojka.poisk.R;
import com.mojka.poisk.data.api.APIGenerator;
import com.mojka.poisk.data.api.inrerfaces.LoginAPI;
import com.mojka.poisk.data.callback.Callback;
import com.mojka.poisk.data.model.LoginResponse;
import com.mojka.poisk.data.model.User;
import com.mojka.poisk.ui.contract.RegisterContract;
import com.mojka.poisk.ui.fragment.RegisterFirstStageFragment;
import com.mojka.poisk.ui.fragment.RegisterFourthStageFragment;
import com.mojka.poisk.ui.fragment.RegisterSecondStageFragment;
import com.mojka.poisk.ui.fragment.RegisterThirdStageFragment;
import com.mojka.poisk.ui.presenter.RegisterFirstStagePresenterImpl;
import com.mojka.poisk.ui.presenter.RegisterFourthStagePresenterImpl;
import com.mojka.poisk.ui.presenter.RegisterPresenterImpl;
import com.mojka.poisk.ui.presenter.RegisterSecondStagePresenterImpl;
import com.mojka.poisk.ui.presenter.RegisterThirdStagePresenterImpl;

import retrofit2.Call;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity implements RegisterContract.View {
    private final String TAG = "RegisterActivity";

    private RegisterContract.Presenter presenter = new RegisterPresenterImpl();
    private RegisterFirstStageFragment firstStageFragment = new RegisterFirstStageFragment();
    private RegisterSecondStageFragment secondStageFragment = new RegisterSecondStageFragment();
    private RegisterThirdStageFragment thirdStageFragment = new RegisterThirdStageFragment();
    private RegisterFourthStageFragment fourthStageFragment = new RegisterFourthStageFragment();

    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firstStageFragment.getPresenter().addAuthCallback(new RegisterFirstStagePresenterImpl.AuthCallback() {
            @Override
            public void onStart(String name, String phoneNumber) {
                user.setName(name);
                user.setPhone(phoneNumber);
            }

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

        thirdStageFragment.getPresenter().addAuthCallback(new RegisterThirdStagePresenterImpl.AuthCallback() {
            @Override
            public void onSuccess(String password) {
                user.setPassword(password);
                showFourthStage();
            }
        });

        fourthStageFragment.getPresenter().addAuthCallback(new RegisterFourthStagePresenterImpl.AuthCallback() {
            @Override
            public void onStart(String city, String car) {
                user.setCar(car);
                user.setCity(city);
            }
        });

        presenter.setView(this);
        showFirstStage();
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    String getActivityTitle() {
        return getString(R.string.activity_register);
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

    @Override
    public void showFourthStage() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0, 0)
                .replace(R.id.frame, fourthStageFragment)
                .commit();
    }
}
