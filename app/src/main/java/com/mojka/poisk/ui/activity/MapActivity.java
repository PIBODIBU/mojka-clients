package com.mojka.poisk.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.maps.SupportMapFragment;
import com.mojka.poisk.R;
import com.mojka.poisk.ui.contract.MapContract;
import com.mojka.poisk.ui.presenter.MapPresenterImpl;

public class MapActivity extends BaseNavDrawerActivity implements MapContract.View {
    private MapContract.Presenter presenter = new MapPresenterImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.setView(this);
        presenter.start();
    }

    @Override
    public void setupMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(presenter.getOnMapReadyCallback());
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    String getActivityTitle() {
        return "Карта";
    }

    @Override
    OnCloseButtonListener getOnCloseButtonListener() {
        return null;
    }

    @Override
    protected Boolean showCloseButton() {
        return false;
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public Activity getViewActivity() {
        return this;
    }

    @Override
    public void setupUi() {

    }
}