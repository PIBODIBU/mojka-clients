package com.mojka.poisk.ui.support.map;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.mojka.poisk.R;
import com.mojka.poisk.data.model.Service;
import com.mojka.poisk.ui.contract.MapContract;
import com.mojka.poisk.utils.DateUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {
    private static final String TAG = "CustomInfoWindow";

    private final View markerView;
    private Activity activity;
    private MapContract.Presenter mapPresenter;

    public CustomInfoWindow(Activity activity, MapContract.Presenter mapPresenter) {
        this.activity = activity;
        this.mapPresenter = mapPresenter;
        markerView = activity.getLayoutInflater().inflate(R.layout.custom_info_window, null);
    }

    public View getInfoWindow(Marker marker) {
        render(marker, markerView);
        return markerView;
    }

    public View getInfoContents(Marker marker) {
        return null;
    }

    private void render(final Marker marker, View view) {
        final Service service = mapPresenter.getSelectedService();

        if (service == null)
            return;

        final ImageView imageView = view.findViewById(R.id.iv_image);
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvWorkingHours = view.findViewById(R.id.tv_working_hours);
        TextView tvNearestOrder = view.findViewById(R.id.tv_nearest_order);

        tvName.setText(service.getName());
        tvWorkingHours.setText(service.getWorkingHours());
        tvNearestOrder.setText(DateUtils.millisToPattern(service.getNearestEntry(), DateUtils.PATTERN_DATE));


        if (imageView.getDrawable() == null)
            if (service.getImages() != null && service.getImages().get(0) != null)
                Picasso.with(activity)
                        .load(service.getImages().get(0).getUrl())
                        .resize(300, 300)
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                Picasso.with(activity)
                                        .load(service.getImages().get(0).getUrl())
                                        .resize(300, 300)
                                        .into(imageView);
                                marker.showInfoWindow();
                            }

                            @Override
                            public void onError() {

                            }
                        });
            else {
                if (service.getImages() != null && service.getImages().get(0) != null)
                    Picasso.with(activity)
                            .load(service.getImages().get(0).getUrl())
                            .into(imageView);
            }
    }
}