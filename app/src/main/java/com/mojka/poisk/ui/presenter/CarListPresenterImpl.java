package com.mojka.poisk.ui.presenter;

import com.mojka.poisk.R;
import com.mojka.poisk.data.account.AccountService;
import com.mojka.poisk.data.api.APIGenerator;
import com.mojka.poisk.data.api.inrerfaces.CarAPI;
import com.mojka.poisk.data.callback.Callback;
import com.mojka.poisk.data.model.BaseDataWrapper;
import com.mojka.poisk.data.model.Car;
import com.mojka.poisk.ui.adapter.CarListAdapter;
import com.mojka.poisk.ui.contract.CarListContract;

import java.util.List;

import retrofit2.Call;

public class CarListPresenterImpl implements CarListContract.Presenter {
    private CarListContract.View view;
    private CarListAdapter adapter;

    @Override
    public void start() {
        fetchCarList();
    }

    @Override
    public void setView(CarListContract.View view) {
        this.view = view;
    }

    @Override
    public void fetchCarList() {
        view.showProgressBar();

        APIGenerator.createService(CarAPI.class).getCars(new AccountService(view.getViewActivity()).getToken()).enqueue(new Callback<BaseDataWrapper<List<Car>>>() {
            @Override
            public void onSuccess(BaseDataWrapper<List<Car>> response) {
                if (response.getError()) {
                    onError();
                    return;
                }

                setupAdapter(response.getResponseObj());
                view.setupUi();
            }

            @Override
            public void onFailure(Call<BaseDataWrapper<List<Car>>> call, Throwable t) {
                view.showToast(R.string.loading_error);
            }

            @Override
            public void onDone() {
                view.hideProgressBar();
            }
        });
    }

    @Override
    public void setupAdapter(List<Car> cars) {
        adapter = new CarListAdapter(cars, view.getViewActivity());
    }

    @Override
    public CarListAdapter getAdapter() {
        return adapter;
    }
}
