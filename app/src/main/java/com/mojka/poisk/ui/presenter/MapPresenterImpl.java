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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mojka.poisk.R;
import com.mojka.poisk.data.api.APIGenerator;
import com.mojka.poisk.data.api.inrerfaces.ServiceAPI;
import com.mojka.poisk.data.callback.Callback;
import com.mojka.poisk.data.model.BaseDataWrapper;
import com.mojka.poisk.data.model.MapFilter;
import com.mojka.poisk.data.model.Service;
import com.mojka.poisk.data.model.ServiceType;
import com.mojka.poisk.ui.activity.MapActivity;
import com.mojka.poisk.ui.contract.MapContract;
import com.mojka.poisk.ui.support.map.CustomInfoWindow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;

public class MapPresenterImpl implements MapContract.Presenter {
    private static final String TAG = "MapPresenterImpl";

    private MapContract.View view;
    private GoogleMap map;
    private MapFilter mapFilter;
    private FusedLocationProviderClient locationProviderClient;
    private GoogleApiClient googleApiClient;
    private Marker userPositionMarker;
    private HashMap<Marker, Service> services = new HashMap<>();
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
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (marker.equals(userPositionMarker))
                            return true;
                        else {
                            setSelectedService(services.get(marker));
                            return false;
                        }
                    }
                });

                fetchServices(new Callback<BaseDataWrapper<List<Service>>>() {
                    @Override
                    public void onSuccess(BaseDataWrapper<List<Service>> response) {
                        if (!response.getError()) {
                            for (Service service : response.getResponseObj()) {
                                Marker marker = map.addMarker(new MarkerOptions()
                                        .position(new LatLng(service.getLat(), service.getLng()))
                                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

                                services.put(marker, service);
                            }
                        }
                    }
                });
            }
        };
    }

    @Override
    public void setView(MapContract.View view) {
        this.view = view;
    }
}
