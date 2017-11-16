package com.mojka.poisk.ui.support.map;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.mojka.poisk.R;
import com.squareup.picasso.Picasso;

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {
    private final View markerView;
    private Activity activity;

    public CustomInfoWindow(Activity activity) {
        this.activity = activity;
        markerView = activity.getLayoutInflater().inflate(R.layout.custom_info_window, null);
    }

    public View getInfoWindow(Marker marker) {
        render(marker, markerView);
        return markerView;
    }

    public View getInfoContents(Marker marker) {
        return null;
    }

    private void render(Marker marker, View view) {
        ImageView imageView = view.findViewById(R.id.iv_image);

        Picasso.with(activity)
                .load(R.drawable.img_info_window)
                .into(imageView);
    }
}