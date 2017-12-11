package com.mojka.poisk.ui.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.mojka.poisk.R;
import com.mojka.poisk.data.account.AccountService;
import com.mojka.poisk.data.api.APIGenerator;
import com.mojka.poisk.data.api.inrerfaces.CarAPI;
import com.mojka.poisk.data.callback.Callback;
import com.mojka.poisk.data.model.BaseDataWrapper;
import com.mojka.poisk.data.model.BaseErrorResponse;
import com.mojka.poisk.data.model.Car;
import com.mojka.poisk.data.model.Image;
import com.mojka.poisk.ui.adapter.CarEditAdapter;
import com.mojka.poisk.ui.contract.CarEditContract;
import com.mojka.poisk.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.mojka.poisk.ui.activity.CarEditActivity.KEY_CAR_ID;

public class CarEditPresenterImpl implements CarEditContract.Presenter {
    private static final String TAG = "CarEditPresenterImpl";
    private CarEditContract.View view;
    private Integer carId;
    private Car car;
    private CarEditAdapter adapter;

    @Override
    public void start() {
        if (!checkIntent(view.getViewActivity().getIntent()))
            return;

        carId = fetchCarIdFromIntent(view.getViewActivity().getIntent());

        fetchCar();
    }

    @Override
    public void setView(CarEditContract.View view) {
        this.view = view;
    }

    @Override
    public void updateCar() {
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

        APIGenerator.createService(CarAPI.class).updateCar(
                RequestBody.create(MediaType.parse("x-www-form-urlencoded"), String.valueOf(carId)),
                RequestBody.create(MediaType.parse("x-www-form-urlencoded"), new AccountService(view.getViewActivity()).getToken()),
                RequestBody.create(MediaType.parse("x-www-form-urlencoded"), car.getName()),
                RequestBody.create(MediaType.parse("x-www-form-urlencoded"), car.getNumbers()),
                imageParts
        ).enqueue(new Callback<BaseErrorResponse>() {
            @Override
            public void onSuccess(BaseErrorResponse response) {
                if (response.getError()) {
                    onError();
                    return;
                }

                view.getViewActivity().setResult(Activity.RESULT_OK);
                view.getViewActivity().finish();
            }

            @Override
            public void onError() {
                view.showToast(R.string.error);
            }
        });
    }

    @Override
    public Car getCar() {
        return car;
    }

    @Override
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public void fetchCar() {
        APIGenerator.createService(CarAPI.class).getCars(new AccountService(view.getViewContext()).getToken())
                .enqueue(new Callback<BaseDataWrapper<List<Car>>>() {
                    @Override
                    public void onSuccess(BaseDataWrapper<List<Car>> response) {
                        if (response.getError()) {
                            onError();
                            return;
                        }

                        for (Car car : response.getResponseObj())
                            if (car.getId() == carId)
                                setCar(car);

                        if (getCar() == null)
                            onError();

                        view.getViewBinding().setCar(getCar());
                        setupAdapter(getCar());
                        view.setupUi();
                    }

                    @Override
                    public void onError() {
                        view.showToast(R.string.error);
                    }

                    @Override
                    public void onDone() {
                    }
                });
    }

    @Override
    public void onSelectImage(Uri uri) {
        adapter.addImage(uri);
    }

    @Override
    public void setupAdapter(Car car) {
        adapter = new CarEditAdapter(view.getViewContext());

        adapter.setOnDeleteClickListener(uri -> adapter.removeImage(uri));
    }

    @Override
    public CarEditAdapter getAdapter() {
        return adapter;
    }

    @Override
    public Boolean checkIntent(Intent intent) {
        return !(intent == null || intent.getExtras() == null || !intent.getExtras().containsKey(KEY_CAR_ID));
    }

    @Override
    public Integer fetchCarIdFromIntent(Intent intent) {
        try {
            return intent.getExtras().getInt(KEY_CAR_ID, -1);
        } catch (Exception ex) {
            return -1;
        }
    }
}
