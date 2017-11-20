package com.mojka.poisk.ui.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mojka.poisk.R;
import com.mojka.poisk.data.model.MapFilter;
import com.mojka.poisk.data.model.Service;
import com.mojka.poisk.ui.activity.MapActivity;
import com.mojka.poisk.ui.contract.MapContract;
import com.mojka.poisk.ui.support.map.CustomInfoWindow;

public class MapPresenterImpl implements MapContract.Presenter {
    private MapContract.View view;
    private GoogleMap map;
    private MapFilter mapFilter;

    @Override
    public void start() {
        view.setupMap();
        fetchFilter();
        setupBottomBar();
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
        return mapFilter != null && mapFilter.getServices().size() > 0;
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

            for (Service service : mapFilter.getServices())
                serviceNames = serviceNames.concat(service.getName().concat(", "));

            serviceNames = serviceNames.substring(0, serviceNames.length() - 2);

            view.setServiceName(serviceNames);
        } else {
            view.setBottomBarTitle(R.string.tv_service_title_one);
            view.setServiceName(mapFilter.getServices().get(0).getName());
        }
    }

    @Override
    public Boolean hasManyServicesInFilter() {
        if (!hasFilter())
            return false;

        if (mapFilter.getServices() == null)
            return false;

        return mapFilter.getServices().size() > 1;
    }

    @Override
    public OnMapReadyCallback getOnMapReadyCallback() {
        return new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.setInfoWindowAdapter(new CustomInfoWindow(view.getViewActivity()));

                LatLng sydney = new LatLng(-34, 151);

                int height = 61;
                int width = 36;
                BitmapDrawable bitmapDrawable = (BitmapDrawable) view.getViewActivity().getResources().getDrawable(R.drawable.ic_map_marker);
                Bitmap b = bitmapDrawable.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                Marker marker = map.addMarker(new MarkerOptions()
                        .position(sydney)
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

                map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        };
    }

    @Override
    public void setView(MapContract.View view) {
        this.view = view;
    }
}
