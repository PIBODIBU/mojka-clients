package com.mojka.poisk.ui.presenter;

import com.mojka.poisk.ui.contract.MapFilterWindowContract;

public class MapFilterWindowPresenterImpl implements MapFilterWindowContract.Presenter {
    private MapFilterWindowContract.View view;

    @Override
    public void start() {
        view.setupUi();
    }

    @Override
    public void setView(MapFilterWindowContract.View view) {
        this.view = view;
    }
}
