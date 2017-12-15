package com.mojka.poisk.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mojka.poisk.R;
import com.mojka.poisk.data.account.AccountService;
import com.mojka.poisk.ui.contract.ProfileContract;
import com.mojka.poisk.ui.contract.SettingsCityContract;
import com.mojka.poisk.ui.fragment.SettingsCityFragment;
import com.mojka.poisk.ui.presenter.ProfilePresenterImpl;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfileActivity extends BaseNavDrawerActivity implements ProfileContract.View {
    private final String TAG = "ProfileActivity";

    @BindView(R.id.iv_background)
    public ImageView ivBackground;

    @BindView(R.id.tv_city)
    public TextView tvCity;

    private ProfileContract.Presenter presenter = new ProfilePresenterImpl();
    private SettingsCityContract.View settingsMVP;
    private AccountService accountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            checkPermission();
        else
            onPermissionsGranted();
    }

    @Override
    public void onBackPressed() {
        if (settingsMVP.isShowing())
            settingsMVP.hide();
        else
            super.onBackPressed();
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    onPermissionsGranted();
                } else {
                    // permission denied
                    checkPermission();
                }
            }
        }
    }

    @Override
    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        } else {
            onPermissionsGranted();
        }
    }

    @Override
    public void onPermissionsGranted() {
        presenter.setView(this);
        presenter.start();
    }

    @Override
    @OnClick(R.id.ib_settings)
    public void toggleSettingsWindow() {
        if (settingsMVP == null)
            return;

        if (settingsMVP.isShowing()) {
            settingsMVP.hide();
        } else {
            settingsMVP.show();
        }
    }

    @Override
    String getActivityTitle() {
        return getString(R.string.activity_profile);
    }

    @Override
    OnCloseButtonListener getOnCloseButtonListener() {
        return this::finish;
    }

    @Override
    @OnClick(R.id.btn_choose_service)
    public void chooseService() {
        startActivity(new Intent(ProfileActivity.this, MapFilterActivity.class));
        finish();
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    protected Boolean showCloseButton() {
        return false;
    }

    @Override
    public Activity getViewActivity() {
        return this;
    }

    @Override
    public void setUserCity(String city) {
        settingsMVP.setCity(city);
    }

    @Override
    @OnClick(R.id.btn_order_history)
    public void showOrderHistory() {
        startActivity(new Intent(ProfileActivity.this, OrderListActivity.class)
                .putExtra(OrderListActivity.INTENT_KEY_PAGE, OrderListActivity.PAGE_ORDER_HISTORY));
        finish();
    }

    @Override
    @OnClick(R.id.btn_my_car)
    public void showMyCar() {
        startActivity(new Intent(ProfileActivity.this, CarListActivity.class));
        finish();
    }

    @Override
    public void setupUi() {
        accountService = new AccountService(this);

        Picasso.with(this)
                .load(R.drawable.img_profile_bg)
                .into(ivBackground);

        if (accountService.getAccount().getCity() != null)
            tvCity.setText(accountService.getAccount().getCity());

        settingsMVP = ((SettingsCityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_settings)).getMVPView();
        settingsMVP.hide();
        settingsMVP.getPresenter().setOnCityChangeListener(city -> tvCity.setText(city.getName()));
    }
}
