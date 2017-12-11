package com.mojka.poisk.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.contract.CarListContract;
import com.mojka.poisk.ui.presenter.CarListPresenterImpl;
import com.mojka.poisk.ui.support.ItemOffsetDecoration;

import butterknife.BindView;
import butterknife.OnClick;

public class CarListActivity extends BaseNavDrawerActivity implements CarListContract.View {
    private static final String TAG = "CarListActivity";
    public static final int REQUEST_CAR_ADD = 0;
    public static final int REQUEST_CAR_EDIT = 1;

    private CarListContract.Presenter presenter = new CarListPresenterImpl();

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    @BindView(R.id.c_progress_bar)
    public View cProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.setView(this);
        presenter.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CAR_ADD:
                if (resultCode == RESULT_OK)
                    showToast(R.string.toast_car_added);

                presenter.fetchCarList();
                return;
            case REQUEST_CAR_EDIT:
                if (resultCode == RESULT_OK)
                    showToast(R.string.toast_car_edited);

                presenter.fetchCarList();
                return;
            default:
                return;
        }
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_car_list;
    }

    @Override
    String getActivityTitle() {
        return getString(R.string.activity_car_list);
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
    public Context getViewContext() {
        return this;
    }

    @Override
    public Activity getViewActivity() {
        return this;
    }

    @Override
    public void setupUi() {
        recyclerView.addItemDecoration(new ItemOffsetDecoration(this, R.dimen.grid_manager_spacing));

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(presenter.getAdapter());
    }

    @Override
    public void showToast(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        cProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        cProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    @OnClick(R.id.fab_add_car)
    public void addCar() {
        startActivityForResult(new Intent(CarListActivity.this, CarAddActivity.class), REQUEST_CAR_ADD);
    }
}
