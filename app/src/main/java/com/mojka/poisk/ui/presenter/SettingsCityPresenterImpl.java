package com.mojka.poisk.ui.presenter;

import com.mojka.poisk.data.model.City;
import com.mojka.poisk.ui.adapter.SettingsCityAdapter;
import com.mojka.poisk.ui.contract.SettingsCityContract;

public class SettingsCityPresenterImpl implements SettingsCityContract.Presenter {
    private SettingsCityContract.View view;
    private SettingsCityAdapter adapter = new SettingsCityAdapter();

    @Override
    public void start() {
        fetchCities();
        setupAdapter();
        view.setupRecyclerView();
    }

    @Override
    public void fetchCities() {
        adapter.addItem(new City("City #1"));
        adapter.addItem(new City("City #2"));
        adapter.addItem(new City("City #3"));
        adapter.addItem(new City("City #4"));
        adapter.addItem(new City("City #5"));
        adapter.addItem(new City("City #6"));
        adapter.addItem(new City("City #7"));
        adapter.addItem(new City("City #8"));
        adapter.addItem(new City("City #9"));
        adapter.addItem(new City("City #10"));
        adapter.addItem(new City("City #11"));
    }

    @Override
    public void setupAdapter() {
        adapter.setOnItemClickListener(new SettingsCityAdapter.OnItemClickListener() {
            @Override
            public void onClick(City city) {
                view.showToast(city.getName());
            }
        });
    }

    @Override
    public SettingsCityAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void setView(SettingsCityContract.View view) {
        this.view = view;
    }
}
