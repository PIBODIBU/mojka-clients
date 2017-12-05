package com.mojka.poisk.ui.presenter;

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

import java.util.List;

public class SettingsCityPresenterImpl implements SettingsCityContract.Presenter {
    private static final String TAG = "SettingsCityPresenter";

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
        view.showProgressBar();

        APIGenerator.createService(MiscAPI.class).getCities().enqueue(new Callback<BaseDataWrapper<List<City>>>() {
            @Override
            public void onSuccess(BaseDataWrapper<List<City>> response) {
                if (response.getError()) {
                    onError();
                    return;
                }

                for (City city : response.getResponseObj())
                    adapter.addItem(city);
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
