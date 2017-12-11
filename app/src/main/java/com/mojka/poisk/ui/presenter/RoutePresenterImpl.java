package com.mojka.poisk.ui.presenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.config.GoogleDirectionConfiguration;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.constant.Language;
import com.akexorcist.googledirection.constant.Unit;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mojka.poisk.R;
import com.mojka.poisk.ui.activity.RouteActivity;
import com.mojka.poisk.ui.contract.RouteContract;

import java.util.ArrayList;

public class RoutePresenterImpl implements RouteContract.Presenter {
    private static final String TAG = "RoutePresenterImpl";

    private RouteContract.View view;
    private GoogleMap map;
    private FusedLocationProviderClient locationProviderClient;
    private GoogleApiClient googleApiClient;

    private LatLng location;
    private LatLng userLocation;

    @Override
    public void start() {
        if (!checkIntent())
            return;

        view.setupUi();
        view.setupMap();
    }

    @Override
    public void setView(RouteContract.View view) {
        this.view = view;
    }

    @Override
    public Boolean checkIntent() {
        Intent intent = view.getViewActivity().getIntent();

        if (intent == null || intent.getExtras() == null ||
                !intent.getExtras().containsKey(RouteActivity.KEY_LATITUDE) ||
                !intent.getExtras().containsKey(RouteActivity.KEY_LONGITUDE))
            return false;

        location = new LatLng(
                intent.getExtras().getDouble(RouteActivity.KEY_LATITUDE),
                intent.getExtras().getDouble(RouteActivity.KEY_LONGITUDE));

        return true;
    }

    @Override
    public OnMapReadyCallback getOnMapReadyCallBack() {
        return googleMap -> {
            map = googleMap;
            setupGoogleApi();
        };
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
                        getUserLocation();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                    }
                })
                .addOnConnectionFailedListener(connectionResult -> {
                })
                .build();

        googleApiClient.connect();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void getUserLocation() {
        locationProviderClient.getLastLocation()
                .addOnSuccessListener(view.getViewActivity(), location -> {
                            if (location == null)
                                return;

                            userLocation = new LatLng(location.getLatitude(), location.getLongitude());

                            int height = 50;
                            int width = 50;
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) view.getViewActivity().getResources().getDrawable(R.drawable.ic_marker_my_location);
                            Bitmap b = bitmapDrawable.getBitmap();
                            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                            map.addMarker(new MarkerOptions()
                                    .flat(true)
                                    .position(new LatLng(userLocation.latitude, userLocation.longitude))
                                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

                            createRoute();
                        }
                );
    }

    @Override
    public void createRoute() {
        if (location == null || userLocation == null) {
            Log.e(TAG, "createRoute: null");
            return;
        }

        int markerHeight = 61;
        int markerWidth = 36;
        BitmapDrawable bitmapDrawable = (BitmapDrawable) view.getViewActivity().getResources().getDrawable(R.drawable.ic_map_marker);
        Bitmap b = bitmapDrawable.getBitmap();
        final Bitmap smallMarker = Bitmap.createScaledBitmap(b, markerWidth, markerHeight, false);
        map.addMarker(new MarkerOptions()
                .position(new LatLng(location.latitude, location.longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

        GoogleDirectionConfiguration.getInstance().setLogEnabled(true);
        GoogleDirection
                .withServerKey(view.getViewActivity().getString(R.string.maps_api_key))
                .from(userLocation)
                .to(location)
                .language(Language.RUSSIAN)
                .unit(Unit.METRIC)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if (!direction.isOK()) {
                            Log.e(TAG, "onDirectionSuccess: not OK");
                            return;
                        }

                        ArrayList<LatLng> points = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                        PolylineOptions polylineOptions = DirectionConverter.createPolyline(
                                view.getViewContext(),
                                points,
                                5,
                                ContextCompat.getColor(view.getViewContext(), R.color.colorPrimary));

                        map.addPolyline(polylineOptions);
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                    }
                });
    }
}
