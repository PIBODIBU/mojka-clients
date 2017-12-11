package com.mojka.poisk.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.mojka.poisk.R;
import com.mojka.poisk.data.account.AccountService;
import com.mojka.poisk.data.model.Image;
import com.mojka.poisk.data.model.Service;
import com.mojka.poisk.databinding.ActivityServiceDetailsBinding;
import com.mojka.poisk.ui.contract.ServiceDetailsContract;
import com.mojka.poisk.ui.dialog.DialogServiceDetailsPhoto;
import com.mojka.poisk.ui.presenter.ServiceDetailsPresenterImpl;
import com.takisoft.datetimepicker.DatePickerDialog;
import com.takisoft.datetimepicker.TimePickerDialog;
import com.takisoft.datetimepicker.widget.DatePicker;
import com.takisoft.datetimepicker.widget.TimePicker;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

public class ServiceDetailsActivity extends BaseNavDrawerActivity implements ServiceDetailsContract.View {
    public static final String TAG = "ServiceDetailsActivity";
    public static final String INTENT_KEY_SERVICE_ID = "INTENT_KEY_SERVICE_ID";
    private static final int CODE_LOGIN_ACTIVITY = 0;

    private ServiceDetailsContract.Presenter presenter = new ServiceDetailsPresenterImpl();
    private Calendar newOrderDate = Calendar.getInstance();
    private AccountService accountService;

    @BindView(R.id.slider_images)
    public SliderLayout sliderLayout;

    @BindView(R.id.c_progress_bar)
    public View cProgressBar;

    @BindView(R.id.c_error)
    public View cError;

    @BindView(R.id.tv_error)
    public TextView tvError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountService = new AccountService(this);

        presenter.setView(this);
        presenter.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_LOGIN_ACTIVITY)
            recreate();
    }

    @Override
    protected void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_service_details;
    }

    @Override
    String getActivityTitle() {
        return getString(R.string.activity_service_details);
    }

    @Override
    protected ActivityServiceDetailsBinding getBinding() {
        return ((ActivityServiceDetailsBinding) super.getBinding());
    }

    @Override
    @OnClick(R.id.btn_create_route)
    public void openCreateRouteActivity() {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" +
                String.valueOf(presenter.getService().getLat()) +
                "," +
                String.valueOf(presenter.getService().getLng()));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

        /*startActivity(new Intent(ServiceDetailsActivity.this, RouteActivity.class)
                .putExtra(RouteActivity.KEY_LATITUDE, presenter.getService().getLat())
                .putExtra(RouteActivity.KEY_LONGITUDE, presenter.getService().getLng()));*/
    }

    @Override
    protected Boolean useDataBinding() {
        return true;
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

    }

    @Override
    public void setupUi(Service service) {
        DialogServiceDetailsPhoto dialogServiceDetailsPhoto = new DialogServiceDetailsPhoto();

        // Refresh binding
        getBinding().setService(service);

        // Activity title
        if (service.getType() == Service.TYPE_WASH)
            setToolbarTitle(R.string.activity_service_details_wash);
        else if (service.getType() == Service.TYPE_REPAIR)
            setToolbarTitle(R.string.activity_service_details_repair);

        // Images slider
        for (Image image : service.getImages())
            if (image != null && image.getUrl() != null && image.getUrl() != "")
                sliderLayout.addSlider(
                        new DefaultSliderView(this)
                                .image(image.getUrl())
                                .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                                .setOnSliderClickListener(slider -> {
                                    dialogServiceDetailsPhoto.setPhotoUrl(slider.getUrl());
                                    dialogServiceDetailsPhoto.show(getSupportFragmentManager(), "dialogServiceDetailsPhoto");
                                }));
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
    @OnClick(R.id.iv_next_slide)
    public void showNextImage() {
        sliderLayout.moveNextPosition(true);
    }

    @Override
    @OnClick(R.id.iv_prev_slide)
    public void showPreviousImage() {
        sliderLayout.movePrevPosition(true);
    }

    @Override
    public void setErrorText(int stringId) {
        if (stringId == -1) {
            cError.setVisibility(View.GONE);
            return;
        }

        cError.setVisibility(View.VISIBLE);
        tvError.setText(stringId);
    }

    @Override
    public void showToast(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Intent getIntentFromView() {
        return getIntent();
    }

    @Override
    @OnClick(R.id.btn_create_order)
    public void createOrder() {
        if (!accountService.isLogged()) {
            startActivityForResult(new Intent(ServiceDetailsActivity.this, LoginActivity.class)
                            .putExtra(LoginActivity.KEY_FINISH_AFTER_SUCCESS, true),
                    CODE_LOGIN_ACTIVITY);
            return;
        }

        Calendar now = Calendar.getInstance();

        final TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                newOrderDate.set(Calendar.HOUR_OF_DAY, view.getHour());
                newOrderDate.set(Calendar.MINUTE, view.getMinute());

                presenter.createOrder(newOrderDate.getTimeInMillis());
            }
        }, 0, 0, true);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                newOrderDate = Calendar.getInstance();

                newOrderDate.set(Calendar.DAY_OF_MONTH, view.getDayOfMonth());
                newOrderDate.set(Calendar.MONTH, view.getMonth());
                newOrderDate.set(Calendar.YEAR, view.getYear());

                timePickerDialog.show();
            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    @Override
    @OnClick(R.id.btn_reload)
    public void reload() {
        presenter.fetchService();
    }
}
