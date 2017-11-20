package com.mojka.poisk.ui.presenter;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mojka.poisk.R;
import com.mojka.poisk.data.api.inrerfaces.GeocoderAPI;
import com.mojka.poisk.data.api.location.APIGeocoder;
import com.mojka.poisk.data.model.GeocoderWrapper;
import com.mojka.poisk.ui.contract.ProfileContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenterImpl implements ProfileContract.Presenter {
    private final String TAG = "ProfilePresenterImpl";
    private ProfileContract.View view;
    private GoogleApiClient googleApiClient;
    private FusedLocationProviderClient locationProviderClient;

    @Override
    public void start() {
        view.setupUi();
        setupGoogleApi();
    }

    @Override
    public void setView(ProfileContract.View view) {
        this.view = view;
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
                                .enqueue(new Callback<GeocoderWrapper>() {
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
}
