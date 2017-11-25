package com.mojka.poisk.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.mojka.poisk.R;
import com.mojka.poisk.ui.contract.MapContract;
import com.mojka.poisk.ui.contract.MapFilterWindowContract;
import com.mojka.poisk.ui.fragment.MapFilterFragment;
import com.mojka.poisk.ui.fragment.SettingsCityFragment;
import com.mojka.poisk.ui.presenter.MapFilterWindowPresenterImpl;
import com.mojka.poisk.ui.presenter.MapPresenterImpl;

import butterknife.BindView;
import butterknife.OnClick;

public class MapActivity extends BaseNavDrawerActivity implements MapContract.View {
    public static final String INTENT_KEY_MAP_FILTER = "INTENT_KEY_MAP_FILTER";

    private MapContract.Presenter presenter = new MapPresenterImpl();
    private MapFilterWindowContract.View filterMVP;

    @BindView(R.id.tv_service_title)
    public TextView tvServiceTitle;

    @BindView(R.id.tv_service_name)
    public TextView tvServiceName;

    @BindView(R.id.c_services)
    public View containerServices;

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
    public void setupFilterWindow() {
        filterMVP = ((MapFilterFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_filter)).getMVPView();
        filterMVP.hide();
    }

    @Override
    @OnClick(R.id.btn_filters)
    public void toggleFilterWindow() {
        if (filterMVP.isShowing()) {
            filterMVP.hide();
        } else {
            filterMVP.show();
        }
    }

    @Override
    public MapFilterWindowContract.View getFilterMVP() {
        return filterMVP;
    }

    @Override
    public void setBottomBarTitle(@StringRes int title) {
        tvServiceTitle.setText(title);
    }

    @Override
    public void setServiceName(String name) {
        tvServiceName.setText(name);
    }

    @Override
    public void showBottomBar() {
        containerServices.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBottomBar() {
        containerServices.setVisibility(View.GONE);
    }

    @Override
    @OnClick(R.id.btn_choose_service)
    public void chooseAnotherFilters() {
        startActivity(new Intent(MapActivity.this, MapFilterActivity.class));
        finish();
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    String getActivityTitle() {
        return getString(R.string.activity_map);
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
        setupFilterWindow();
    }
}