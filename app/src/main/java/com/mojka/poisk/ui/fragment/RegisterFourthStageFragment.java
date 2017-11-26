package com.mojka.poisk.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mojka.poisk.R;
import com.mojka.poisk.ui.activity.RegisterActivity;
import com.mojka.poisk.ui.contract.RegisterContract;
import com.mojka.poisk.ui.presenter.RegisterFourthStagePresenterImpl;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterFourthStageFragment extends BaseFragment implements RegisterContract.FourthStage.View {
    private final String TAG = "RegisterFourthStage";

    @BindView(R.id.progress_view)
    public ProgressView progressView;

    @BindView(R.id.btn_continue)
    public Button btnContinue;

    @BindView(R.id.et_city)
    public EditText etCity;

    @BindView(R.id.et_car)
    public EditText etCar;

    @BindView(R.id.tv_error)
    public TextView tvError;

    private RegisterContract.FourthStage.Presenter presenter = new RegisterFourthStagePresenterImpl();

    @Override
    @SuppressLint("MissingPermission")
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.setView(this);
        presenter.start();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            checkPermission();
        else
            onPermissionsGranted();
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
                }
            }
        }
    }

    @Override
    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(getViewActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getViewActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getViewActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        } else {
            onPermissionsGranted();
        }
    }

    @Override
    public void onPermissionsGranted() {
        presenter.setupGoogleApi();
    }

    @Override
    public void setUserCity(String city) {
        etCity.setText(city);
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public RegisterActivity getViewActivity() {
        return ((RegisterActivity) getActivity());
    }

    @Override
    public void setupUi() {

    }

    @Override
    public void showToast(int text) {
        Toast.makeText(getViewContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getViewContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    @OnClick(R.id.btn_continue)
    public void register() {
        presenter.register(etCity.getText().toString(), etCar.getText().toString());
    }

    @Override
    @OnClick(R.id.btn_skip)
    public void skip() {
        presenter.skip();
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_register_fourth_stage;
    }

    @Override
    public RegisterContract.FourthStage.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void showProgressBar() {
        progressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showButton() {
        btnContinue.setClickable(true);
    }

    @Override
    public void hideButton() {
        btnContinue.setClickable(false);
    }

    @Override
    public void setErrorText(String text) {
        this.tvError.setText(text);
    }
}
