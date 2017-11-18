package com.mojka.poisk.ui.presenter;

import android.text.TextUtils;
import android.widget.Toast;

import com.mojka.poisk.R;
import com.mojka.poisk.data.api.APIGenerator;
import com.mojka.poisk.data.api.inrerfaces.LoginAPI;
import com.mojka.poisk.data.callback.Callback;
import com.mojka.poisk.data.model.LoginResponse;
import com.mojka.poisk.ui.activity.RegisterActivity;
import com.mojka.poisk.ui.contract.RegisterContract;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RegisterFourthStagePresenterImpl implements RegisterContract.FourthStage.Presenter {
    private RegisterContract.FourthStage.View view;
    private LinkedList<AuthCallback> authCallbacks = new LinkedList<>();

    @Override
    public void start() {
        view.setupUi();

        addAuthCallback(new AuthCallback() {
            @Override
            public void onStart(String city, String car) {
                view.setErrorText("");
                view.hideButton();
                view.showProgressBar();
            }

            @Override
            public void onSkip() {
                view.setErrorText("");
                view.hideButton();
                view.showProgressBar();
            }

            @Override
            public void onSuccess() {
                view.showButton();
                view.hideProgressBar();
            }

            @Override
            public void onError() {
                view.setErrorText(view.getViewActivity().getString(R.string.error_auth_phone));
                view.showButton();
                view.hideProgressBar();
            }
        });
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
    public void register(String city, String car) {
        if (TextUtils.isEmpty(city) || TextUtils.isEmpty(car)) {
            view.setErrorText(view.getViewActivity().getString(R.string.error_enter_city_and_car));
            return;
        }

        for (AuthCallback authCallback : getAuthCallbacks())
            authCallback.onStart(city, car);

        APIGenerator.createService(LoginAPI.class).register(
                ((RegisterActivity) view.getViewActivity()).getUser().getPhone(),
                ((RegisterActivity) view.getViewActivity()).getUser().getPassword(),
                ((RegisterActivity) view.getViewActivity()).getUser().getName(),
                ((RegisterActivity) view.getViewActivity()).getUser().getCity(),
                ((RegisterActivity) view.getViewActivity()).getUser().getCar()
        ).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                super.onResponse(call, response);

                if (response.body().getError()) {
                    view.showToast(response.body().getMessage());
                    return;
                }

                view.showToast(response.body().getToken());

                /*startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));
                finish();*/

                for (AuthCallback authCallback : getAuthCallbacks())
                    authCallback.onSuccess();
            }

            @Override
            public void onError() {
                view.showToast(R.string.error_register);

                for (AuthCallback authCallback : getAuthCallbacks())
                    authCallback.onError();
            }

            @Override
            public void onDone() {

            }
        });
    }

    @Override
    public void skip() {
        for (AuthCallback authCallback : getAuthCallbacks())
            authCallback.onSkip();

        APIGenerator.createService(LoginAPI.class).register(
                ((RegisterActivity) view.getViewActivity()).getUser().getPhone(),
                ((RegisterActivity) view.getViewActivity()).getUser().getPassword(),
                ((RegisterActivity) view.getViewActivity()).getUser().getName(),
                ((RegisterActivity) view.getViewActivity()).getUser().getCity(),
                ((RegisterActivity) view.getViewActivity()).getUser().getCar()
        ).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                super.onResponse(call, response);

                if (response.body().getError()) {
                    view.showToast(response.body().getMessage());
                    return;
                }

                view.showToast(response.body().getToken());

                /*startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));
                finish();*/

                for (AuthCallback authCallback : getAuthCallbacks())
                    authCallback.onSuccess();
            }

            @Override
            public void onError() {
                view.showToast(R.string.error_register);
            }

            @Override
            public void onDone() {

            }
        });
    }

    @Override
    public void setView(RegisterContract.FourthStage.View view) {
        this.view = view;
    }

    public static abstract class AuthCallback {
        public void onStart(String city, String car) {
        }

        public void onSuccess() {
        }

        public void onSkip() {
        }

        public void onError() {
        }
    }
}
