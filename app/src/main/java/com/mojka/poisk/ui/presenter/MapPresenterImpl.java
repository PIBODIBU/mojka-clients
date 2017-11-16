package com.mojka.poisk.ui.presenter;

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
import com.mojka.poisk.ui.contract.MapContract;
import com.mojka.poisk.ui.support.map.CustomInfoWindow;

public class MapPresenterImpl implements MapContract.Presenter {
    private MapContract.View view;
    private GoogleMap map;

    @Override
    public void start() {
        view.setupMap();
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

                marker.setInfoWindowAnchor(10.4f, -.1f);
                marker.showInfoWindow();

                map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        };
    }

    @Override
    public void setView(MapContract.View view) {
        this.view = view;
    }
}
