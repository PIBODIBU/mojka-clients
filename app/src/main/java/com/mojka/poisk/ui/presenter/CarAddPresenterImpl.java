package com.mojka.poisk.ui.presenter;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import com.mojka.poisk.data.account.AccountService;
import com.mojka.poisk.data.api.APIGenerator;
import com.mojka.poisk.data.api.inrerfaces.CarAPI;
import com.mojka.poisk.data.callback.Callback;
import com.mojka.poisk.data.model.BaseErrorResponse;
import com.mojka.poisk.data.model.Car;
import com.mojka.poisk.ui.adapter.CarAddAdapter;
import com.mojka.poisk.ui.contract.CarAddContract;
import com.mojka.poisk.utils.FileUtils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
        List<MultipartBody.Part> imageParts = new LinkedList<>();

        for (Uri image : adapter.getImages()) {
            File file = FileUtils.getFile(view.getViewActivity(), image);

            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse(view.getViewActivity().getContentResolver().getType(image)),
                            file);

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("images", file.getName(), requestFile);

            imageParts.add(body);
        }

        try {
            APIGenerator.createService(CarAPI.class).addCar(
                    RequestBody.create(MediaType.parse("x-www-form-urlencoded"), new AccountService(view.getViewActivity()).getToken()),
                    RequestBody.create(MediaType.parse("x-www-form-urlencoded"), car.getName()),
                    RequestBody.create(MediaType.parse("x-www-form-urlencoded"), car.getNumbers()),
                    imageParts
            ).enqueue(new Callback<BaseErrorResponse>() {
                @Override
                public void onSuccess(BaseErrorResponse response) {
                    if (response.getError()) {
                        return;
                    }

                    view.getViewActivity().setResult(Activity.RESULT_OK);
                    view.getViewActivity().finish();
                }

                @Override
                public void onError() {
                    super.onError();
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "addCar: ", ex);
        }
    }

    @Override
    public void onSelectImage(Uri uri) {
        adapter.addImage(uri);
    }

    @Override
    public Car getCar() {
        return car;
    }
}
