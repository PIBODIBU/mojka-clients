package com.mojka.poisk.ui.presenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mojka.poisk.R;
import com.mojka.poisk.data.account.AccountService;
import com.mojka.poisk.data.api.APIGenerator;
import com.mojka.poisk.data.api.inrerfaces.GeocoderAPI;
import com.mojka.poisk.data.api.inrerfaces.ServiceAPI;
import com.mojka.poisk.data.api.location.APIGeocoder;
import com.mojka.poisk.data.callback.Callback;
import com.mojka.poisk.data.model.BaseDataWrapper;
import com.mojka.poisk.data.model.GeocoderWrapper;
import com.mojka.poisk.data.model.MapFilter;
import com.mojka.poisk.data.model.Service;
import com.mojka.poisk.data.model.ServiceType;
import com.mojka.poisk.ui.activity.MapActivity;
import com.mojka.poisk.ui.activity.ServiceDetailsActivity;
import com.mojka.poisk.ui.contract.MapContract;
import com.mojka.poisk.ui.support.map.CustomInfoWindow;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import retrofit2.Call;

public class MapPresenterImpl implements MapContract.Presenter {
    private static final String TAG = "MapPresenterImpl";

    private MapContract.View view;
    private GoogleMap map;
    private MapFilter mapFilter;
    private FusedLocationProviderClient locationProviderClient;
    private GoogleApiClient googleApiClient;
    private Marker userPositionMarker;
    private LinkedList<Service> services = new LinkedList<>();
    private Service selectedService;

    @Override
    public void start() {
        view.setupUi();
        view.setupMap();
        fetchFilter();
        setupBottomBar();
        setupGoogleApi();
    }

    @Override
    public void setupFilterWindow() {
        Integer min = 0;
        Integer max = 0;

        for (Service service : services) {
            if (service.getPriceStart() < min)
                min = service.getPriceStart();
        }

        for (Service service : services) {
            if (service.getPriceEnd() > max)
                max = service.getPriceEnd();
        }

        view.getFilterMVP().setAbsoluteMin(min);
        view.getFilterMVP().setAbsoluteMax(max);

        view.getFilterMVP().getPresenter().setFilterListener(new MapFilterWindowPresenterImpl.FilterListener() {
            @Override
            public void onSave(Integer min, Integer max) {
                for (Service service : services)
                    if (service.getPriceEnd() > max || service.getPriceStart() < min)
                        service.setVisible(false);
                    else
                        service.setVisible(true);

                map.clear();

                for (Service service : services)
                    if (service.getVisible()) {
                        Marker marker = map.addMarker(service.getMarkerOptions());
                        service.setMarker(marker);
                    }

                view.getFilterMVP().hide();
            }
        });
    }

    @Override
    public void fetchFilter() {
        Intent intent = view.getViewActivity().getIntent();

        if (intent == null || intent.getExtras() == null || !intent.getExtras().containsKey(MapActivity.INTENT_KEY_MAP_FILTER))
            return;

        mapFilter = ((MapFilter) intent.getExtras().getSerializable(MapActivity.INTENT_KEY_MAP_FILTER));
    }

    @Override
    public Boolean hasFilter() {
        return mapFilter != null && mapFilter.getServiceTypes().size() > 0;
    }

    @Override
    public void setupBottomBar() {
        if (!hasFilter()) {
            view.hideBottomBar();
            return;
        }

        view.showBottomBar();

        if (hasManyServicesInFilter()) {
            view.setBottomBarTitle(R.string.tv_service_title_many);

            String serviceNames = "";

            for (ServiceType serviceType : mapFilter.getServiceTypes())
                serviceNames = serviceNames.concat(serviceType.getName().concat(", "));

            serviceNames = serviceNames.substring(0, serviceNames.length() - 2);

            view.setServiceName(serviceNames);
        } else {
            view.setBottomBarTitle(R.string.tv_service_title_one);
            view.setServiceName(mapFilter.getServiceTypes().get(0).getName());
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void setupUserLocationUpdates() {
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(3000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (userPositionMarker != null)
                    userPositionMarker.remove();

                int height = 50;
                int width = 50;
                BitmapDrawable bitmapDrawable = (BitmapDrawable) view.getViewActivity().getResources().getDrawable(R.drawable.ic_marker_my_location);
                Bitmap b = bitmapDrawable.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                userPositionMarker = map.addMarker(new MarkerOptions()
                        .flat(true)
                        .position(new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude()))
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
            }
        };

        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    @Override
    public void fetchLocationFromCity() {
        AccountService accountService = new AccountService(view.getViewContext());

        if (!accountService.isLogged())
            return;

        APIGeocoder.createService(GeocoderAPI.class).getLatLngFromCityName(
                accountService.getAccount().getCity(),
                true,
                "ru",
                view.getViewActivity().getString(R.string.key_geocoder)
        ).enqueue(new Callback<GeocoderWrapper>() {
            @Override
            public void onSuccess(GeocoderWrapper response) {
                Double lat = Double.valueOf(response.getGeocoderResults().get(0).getGeometry().getLocation().getLat());
                Double lng = Double.valueOf(response.getGeocoderResults().get(0).getGeometry().getLocation().getLng());

                map.animateCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder()
                                .target(new LatLng(lat, lng))
                                .zoom(10f)
                                .build()
                ));
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
                        setupUserLocationUpdates();
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
    public void fetchServices(Callback<BaseDataWrapper<List<Service>>> callback) {
        Call<BaseDataWrapper<List<Service>>> call = null;

        if (mapFilter == null || mapFilter.getServiceTypes().size() == 0 || mapFilter.getServiceTypes().size() == 2)
            call = APIGenerator.createService(ServiceAPI.class).getAllServices();
        else if (mapFilter.getServiceTypes().contains(ServiceType.CarWash.get()))
            call = APIGenerator.createService(ServiceAPI.class).getWashServices();
        else if (mapFilter.getServiceTypes().contains(ServiceType.WheelRepair.get()))
            call = APIGenerator.createService(ServiceAPI.class).getRepairServices();

        if (call == null)
            return;

        call.enqueue(callback);
    }

    @Override
    public Boolean hasManyServicesInFilter() {
        if (!hasFilter())
            return false;

        if (mapFilter.getServiceTypes() == null)
            return false;

        return mapFilter.getServiceTypes().size() > 1;
    }

    @Override
    public Service getSelectedService() {
        return selectedService;
    }

    @Override
    public void setSelectedService(Service selectedService) {
        this.selectedService = selectedService;
    }

    @Override
    public OnMapReadyCallback getOnMapReadyCallback() {
        return new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                int markerHeight = 61;
                int markerWidth = 36;
                BitmapDrawable bitmapDrawable = (BitmapDrawable) view.getViewActivity().getResources().getDrawable(R.drawable.ic_map_marker);
                Bitmap b = bitmapDrawable.getBitmap();
                final Bitmap smallMarker = Bitmap.createScaledBitmap(b, markerWidth, markerHeight, false);

                map = googleMap;
                map.setInfoWindowAdapter(new CustomInfoWindow(view.getViewActivity(), MapPresenterImpl.this));
                map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Service model = null;

                        for (Service service : services)
                            if (service.getMarker().equals(marker))
                                model = service;

                        if (model != null)
                            view.getViewActivity().startActivity(new Intent(view.getViewActivity(), ServiceDetailsActivity.class)
                                    .putExtra(ServiceDetailsActivity.INTENT_KEY_SERVICE_ID, model.getId()));
                    }
                });
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (marker.equals(userPositionMarker))
                            return true;
                        else {
                            for (Service service : services)
                                if (service.getMarker().equals(marker))
                                    setSelectedService(service);

                            return false;
                        }
                    }
                });

                fetchServices(new Callback<BaseDataWrapper<List<Service>>>() {
                    @Override
                    public void onSuccess(BaseDataWrapper<List<Service>> response) {
                        if (!response.getError()) {
                            for (Service service : response.getResponseObj()) {
                                MarkerOptions markerOptions = new MarkerOptions()
                                        .position(new LatLng(service.getLat(), service.getLng()))
                                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                                Marker marker = map.addMarker(markerOptions);

                                service.setMarker(marker);
                                service.setMarkerOptions(markerOptions);

                                services.add(service);
                            }

                            setupFilterWindow();
                        }
                    }
                });

                fetchLocationFromCity();
            }
        };
    }

    @Override
    public void setView(MapContract.View view) {
        this.view = view;
    }
}
