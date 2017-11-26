package com.mojka.poisk.ui.presenter;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mojka.poisk.R;
import com.mojka.poisk.data.account.AccountService;
import com.mojka.poisk.data.api.APIGenerator;
import com.mojka.poisk.data.api.inrerfaces.GeocoderAPI;
import com.mojka.poisk.data.api.inrerfaces.LoginAPI;
import com.mojka.poisk.data.api.inrerfaces.UserAPI;
import com.mojka.poisk.data.api.location.APIGeocoder;
import com.mojka.poisk.data.callback.Callback;
import com.mojka.poisk.data.model.BaseDataWrapper;
import com.mojka.poisk.data.model.GeocoderWrapper;
import com.mojka.poisk.data.model.LoginResponse;
import com.mojka.poisk.data.model.User;
import com.mojka.poisk.ui.activity.RegisterActivity;
import com.mojka.poisk.ui.contract.RegisterContract;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class RegisterFourthStagePresenterImpl implements RegisterContract.FourthStage.Presenter {
    private final String TAG = "RegisterFourthStage";
    private RegisterContract.FourthStage.View view;
    private LinkedList<AuthCallback> authCallbacks = new LinkedList<>();
    private GoogleApiClient googleApiClient;
    private FusedLocationProviderClient locationProviderClient;

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

    @SuppressLint("MissingPermission")
    @Override
    public void getUserCity() {
        locationProviderClient.getLastLocation()
                .addOnSuccessListener(view.getViewActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location == null) {
                            getUserCity();
                            return;
                        }

                        String latLng = String.valueOf(location.getLatitude()).concat(",").concat(String.valueOf(location.getLongitude()));
                        APIGeocoder.createService(GeocoderAPI.class).getFromLocation(
                                latLng,
                                true,
                                "ru",
                                view.getViewActivity().getString(R.string.key_geocoder))
                                .enqueue(new retrofit2.Callback<GeocoderWrapper>() {
                                    @Override
                                    public void onResponse(Call<GeocoderWrapper> call, Response<GeocoderWrapper> response) {
                                        if (response.body() == null)
                                            return;

                                        try {
                                            view.setUserCity(response.body().getGeocoderResults().get(0).getAddressComponents().get(3).getShortName());
                                            googleApiClient.disconnect();
                                        } catch (Exception ex) {
                                            Log.e(TAG, "onResponse: ", ex);
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<GeocoderWrapper> call, Throwable t) {

                                    }
                                });
                    }
                });
    }

    @Override
    public void setupGoogleApi() {
        googleApiClient = new GoogleApiClient.Builder(view.getViewActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        locationProviderClient = LocationServices.getFusedLocationProviderClient(view.getViewActivity());
                        getUserCity();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .build();

        googleApiClient.connect();
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
            public void onError() {
                view.showToast(R.string.error_register);

                for (AuthCallback authCallback : getAuthCallbacks())
                    authCallback.onError();
            }

            @Override
            public void onSuccess(LoginResponse response) {
                if (response.getError()) {
                    for (AuthCallback authCallback : getAuthCallbacks())
                        authCallback.onError();
                    view.showToast(response.getMessage());
                    return;
                }

                final String token = response.getToken();

                APIGenerator.createService(UserAPI.class).getInfo(token).enqueue(new Callback<BaseDataWrapper<User>>() {
                    @Override
                    public void onSuccess(BaseDataWrapper<User> response) {
                        User user = response.getResponseObj();
                        user.setToken(token);

                        new AccountService(view.getViewContext()).setAccount(response.getResponseObj());

                        for (AuthCallback authCallback : getAuthCallbacks())
                            authCallback.onSuccess();
                    }

                    @Override
                    public void onError() {
                        view.showToast(R.string.error_register);

                        for (AuthCallback authCallback : getAuthCallbacks())
                            authCallback.onError();
                    }
                });
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
                    for (AuthCallback authCallback : getAuthCallbacks())
                        authCallback.onError();
                    view.showToast(response.body().getMessage());
                    return;
                }

                view.showToast(response.body().getToken());

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
