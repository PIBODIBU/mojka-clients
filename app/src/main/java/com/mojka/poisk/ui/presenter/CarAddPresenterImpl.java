package com.mojka.poisk.ui.presenter;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import com.mojka.poisk.data.model.Car;
import com.mojka.poisk.ui.adapter.CarAddAdapter;
import com.mojka.poisk.ui.contract.CarAddContract;

public class CarAddPresenterImpl implements CarAddContract.Presenter {
    private static final String TAG = "CarAddPresenterImpl";

    private CarAddContract.View view;
    private Car car = new Car();
    private CarAddAdapter adapter;

    @Override
    public void start() {
        setupAdapter();
        view.setupUi(car);
    }

    @Override
    public void setupAdapter() {
        adapter = new CarAddAdapter(view.getViewContext());

        adapter.setOnDeleteClickListener(new CarAddAdapter.OnDeleteClickListener() {
            @Override
            public void onDelete(Uri uri) {
                adapter.removeImage(uri);
            }
        });
    }

    @Override
    public CarAddAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void setView(CarAddContract.View view) {
        this.view = view;
    }

    @Override
    public void addCar() {
        view.getViewActivity().setResult(Activity.RESULT_OK);
        view.getViewActivity().finish();
    }

    @Override
    public void onSelectImage(Uri uri) {
        adapter.addImage(uri);
        Log.d(TAG, "c: " + uri.toString());
    }

    @Override
    public Car getCar() {
        return car;
    }
}
