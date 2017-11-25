package com.mojka.poisk.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.mojka.poisk.R;
import com.mojka.poisk.data.model.ServiceType;
import com.mojka.poisk.ui.contract.MapFilterContract;
import com.mojka.poisk.ui.presenter.MapFilterPresenterImpl;

import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class MapFilterActivity extends BaseNavDrawerActivity implements MapFilterContract.View {
    private MapFilterContract.Presenter presenter = new MapFilterPresenterImpl();

    private ServiceType serviceTypeWash = ServiceType.CarWash.get();
    private ServiceType serviceTypeWheel = ServiceType.WheelRepair.get();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.setView(this);
        presenter.start();
    }

    @Override
    @OnClick(R.id.btn_apply)
    public void applyFilters() {
        presenter.apply();
    }

    @Override
    @OnCheckedChanged({R.id.cb_wash, R.id.cb_wheels})
    public void onCheckBoxChanged(CompoundButton checkBox, boolean checked) {
        switch (checkBox.getId()) {
            case R.id.cb_wash:
                if (checked)
                    presenter.addServiceToFilter(serviceTypeWash);
                else
                    presenter.removeServiceFromFilter(serviceTypeWash);

                return;
            case R.id.cb_wheels:
                if (checked)
                    presenter.addServiceToFilter(serviceTypeWheel);
                else
                    presenter.removeServiceFromFilter(serviceTypeWheel);

                return;
        }
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

    @Override
    int getLayoutId() {
        return R.layout.activity_map_filter;
    }

    @Override
    String getActivityTitle() {
        return getString(R.string.activity_map_filter);
    }

    @Override
    OnCloseButtonListener getOnCloseButtonListener() {
        return null;
    }

    @Override
    protected Boolean showCloseButton() {
        return false;
    }
}
