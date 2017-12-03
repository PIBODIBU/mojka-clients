package com.mojka.poisk.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.mojka.poisk.R;
import com.mojka.poisk.data.model.Car;
import com.mojka.poisk.databinding.ActivityCarAddBinding;
import com.mojka.poisk.ui.contract.CarAddContract;
import com.mojka.poisk.ui.presenter.CarAddPresenterImpl;
import com.mojka.poisk.ui.support.ItemOffsetDecoration;

import butterknife.BindView;
import butterknife.OnClick;

public class CarAddActivity extends BaseNavDrawerActivity implements CarAddContract.View {
    private static final String TAG = "CarAddActivity";
    private static final int REQUEST_SELECT_FILE = 0;

    private CarAddContract.Presenter presenter = new CarAddPresenterImpl();

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.setView(this);
        presenter.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_SELECT_FILE)
                if (data != null && data.getData() != null)
                    presenter.onSelectImage(data.getData());
        }
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_car_add;
    }

    @Override
    String getActivityTitle() {
        return getString(R.string.activity_car_add);
    }

    @Override
    OnCloseButtonListener getOnCloseButtonListener() {
        return null;
    }

    @Override
    protected Boolean showCloseButton() {
        return false;
    }

    @Override
    protected Boolean useDataBinding() {
        return true;
    }

    @Override
    protected ActivityCarAddBinding getBinding() {
        return ((ActivityCarAddBinding) super.getBinding());
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public Activity getViewActivity() {
        return this;
    }

    @Override
    public void setupUi() {
    }

    @Override
    public void setupUi(Car car) {
        getBinding().setCar(presenter.getCar());

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new ItemOffsetDecoration(this, R.dimen.grid_manager_spacing));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(presenter.getAdapter());
    }

    @Override
    public void showToast(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    @OnClick(R.id.btn_add)
    public void addCar() {
        presenter.addCar();
    }

    @Override
    @OnClick(R.id.btn_add_photo)
    public void addPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, getString(R.string.intent_select_photo)), REQUEST_SELECT_FILE);
    }
}
