package com.mojka.poisk.ui.presenter;

import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.mojka.poisk.R;
import com.mojka.poisk.data.account.AccountService;
import com.mojka.poisk.data.api.APIGenerator;
import com.mojka.poisk.data.api.inrerfaces.CarAPI;
import com.mojka.poisk.data.api.location.APIGeocoder;
import com.mojka.poisk.data.callback.Callback;
import com.mojka.poisk.data.model.BaseDataWrapper;
import com.mojka.poisk.data.model.BaseErrorResponse;
import com.mojka.poisk.data.model.Car;
import com.mojka.poisk.ui.activity.CarAddActivity;
import com.mojka.poisk.ui.activity.CarEditActivity;
import com.mojka.poisk.ui.activity.CarListActivity;
import com.mojka.poisk.ui.adapter.CarListAdapter;
import com.mojka.poisk.ui.contract.CarListContract;

import java.util.List;

import retrofit2.Call;

public class CarListPresenterImpl implements CarListContract.Presenter, CarListAdapter.ActionListener {
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
        adapter.setActionListener(this);
    }

    @Override
    public void onItemEdit(Car car) {
        view.getViewActivity().startActivityForResult(new Intent(view.getViewContext(), CarEditActivity.class)
                .putExtra(CarEditActivity.KEY_CAR_ID, car.getId()), CarListActivity.REQUEST_CAR_EDIT);
    }

    @Override
    public void onItemDelete(Car car) {
        new AlertDialog.Builder(view.getViewActivity())
                .setCancelable(false)
                .setTitle(R.string.dialog_car_delete_title)
                .setMessage(R.string.dialog_car_delete_message)
                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                    APIGenerator.createService(CarAPI.class).deleteCar(
                            new AccountService(view.getViewActivity()).getToken(),
                            car.getId()
                    ).enqueue(new Callback<BaseErrorResponse>() {
                        @Override
                        public void onSuccess(BaseErrorResponse response) {
                            if (response.getError()) {
                                onError();
                                return;
                            }

                            fetchCarList();
                        }

                        @Override
                        public void onError() {
                            view.showToast(R.string.error);
                        }
                    });
                })
                .setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.cancel())
                .create()
                .show();
    }

    @Override
    public CarListAdapter getAdapter() {
        return adapter;
    }
}
