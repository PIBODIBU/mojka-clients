package com.mojka.poisk.ui.presenter;

import android.content.Intent;

import com.mojka.poisk.data.model.MapFilter;
import com.mojka.poisk.data.model.Service;
import com.mojka.poisk.ui.activity.MapActivity;
import com.mojka.poisk.ui.contract.MapFilterContract;

public class MapFilterPresenterImpl implements MapFilterContract.Presenter {
    private MapFilterContract.View view;
    private MapFilter mapFilter = new MapFilter();

    @Override
    public void start() {
        view.setupUi();
    }

    @Override
    public void apply() {
        view.getViewActivity().startActivity(new Intent(view.getViewActivity(), MapActivity.class)
                .putExtra(MapActivity.INTENT_KEY_MAP_FILTER, mapFilter));
        view.getViewActivity().finish();
    }

    @Override
    public void addServiceToFilter(Service service) {
        mapFilter.addService(service);
    }

    @Override
    public void removeServiceFromFilter(Service service) {
        mapFilter.removeService(service);
    }

    @Override
    public void setView(MapFilterContract.View view) {
        this.view = view;
    }
}
