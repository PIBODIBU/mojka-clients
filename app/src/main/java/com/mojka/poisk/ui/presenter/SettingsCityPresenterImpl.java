package com.mojka.poisk.ui.presenter;

import android.util.Log;

import com.mojka.poisk.R;
import com.mojka.poisk.data.account.AccountService;
import com.mojka.poisk.data.api.APIGenerator;
import com.mojka.poisk.data.api.inrerfaces.MiscAPI;
import com.mojka.poisk.data.api.inrerfaces.UserAPI;
import com.mojka.poisk.data.callback.Callback;
import com.mojka.poisk.data.model.BaseDataWrapper;
import com.mojka.poisk.data.model.BaseErrorResponse;
import com.mojka.poisk.data.model.City;
import com.mojka.poisk.ui.adapter.SettingsCityAdapter;
import com.mojka.poisk.ui.contract.SettingsCityContract;

import java.util.LinkedList;
import java.util.List;

public class SettingsCityPresenterImpl implements SettingsCityContract.Presenter {
    private static final String TAG = "SettingsCityPresenter";

    private SettingsCityContract.View view;
    private SettingsCityAdapter adapter = new SettingsCityAdapter();
    private OnCityChangeListener onCityChangeListener;
    private List<City> dataSet = new LinkedList<>();

    @Override
    public void start() {
        fetchCities();
        setupAdapter();
        view.setupUi();
    }

    @Override
    public void fetchCities() {
        view.showProgressBar();

        APIGenerator.createService(MiscAPI.class).getCities().enqueue(new Callback<BaseDataWrapper<List<City>>>() {
            @Override
            public void onSuccess(BaseDataWrapper<List<City>> response) {
                if (response.getError()) {
                    onError();
                    return;
                }

                dataSet.clear();
                dataSet.addAll(response.getResponseObj());

                adapter.clearDataSet();
                adapter.add(dataSet);
            }

            @Override
            public void onError() {
            }

            @Override
            public void onDone() {
                view.hideProgressBar();
            }
        });
    }

    @Override
    public void onUserInputChanged(String city) {
        adapter.filter(dataSet, city);
    }

    @Override
    public void setupAdapter() {
        adapter.setOnItemClickListener(city -> {
            view.setCity(city.getName());
        });
    }

    @Override
    public void setOnCityChangeListener(OnCityChangeListener onCityChangeListener) {
        this.onCityChangeListener = onCityChangeListener;
    }

    @Override
    public SettingsCityAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void setView(SettingsCityContract.View view) {
        this.view = view;
    }

    @Override
    public void save(final String city) {
        view.showProgressBar();

        APIGenerator.createService(UserAPI.class).updateCity(
                new AccountService(view.getViewContext()).getToken(),
                city).enqueue(new Callback<BaseErrorResponse>() {
            @Override
            public void onSuccess(BaseErrorResponse response) {
                if (response.getError()) {
                    onError();
                    return;
                }

                new AccountService(view.getViewContext()).setParam(AccountService.KEY_CITY, city);
                view.showToast(view.getViewActivity().getString(R.string.saved));
                view.hide();

                if (onCityChangeListener != null) {
                    onCityChangeListener.onCityUpdated(new City(city));
                }
            }

            @Override
            public void onError() {
                view.showToast(view.getViewActivity().getString(R.string.error));
            }

            @Override
            public void onDone() {
                view.hideProgressBar();
            }
        });
    }
}
