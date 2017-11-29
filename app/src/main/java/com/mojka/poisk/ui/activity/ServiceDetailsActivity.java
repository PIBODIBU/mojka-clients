package com.mojka.poisk.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.mojka.poisk.R;
import com.mojka.poisk.data.model.Image;
import com.mojka.poisk.data.model.Service;
import com.mojka.poisk.databinding.ActivityServiceDetailsBinding;
import com.mojka.poisk.ui.contract.ServiceDetailsContract;
import com.mojka.poisk.ui.presenter.ServiceDetailsPresenterImpl;

import butterknife.BindView;
import butterknife.OnClick;

public class ServiceDetailsActivity extends BaseNavDrawerActivity implements ServiceDetailsContract.View {
    public static final String TAG = "ServiceDetailsActivity";
    public static final String INTENT_KEY_SERVICE_ID = "INTENT_KEY_SERVICE_ID";

    private ServiceDetailsContract.Presenter presenter = new ServiceDetailsPresenterImpl();

    @BindView(R.id.slider_images)
    public SliderLayout sliderLayout;

    @BindView(R.id.slider_indicator)
    public PagerIndicator sliderIndicator;

    @BindView(R.id.c_progress_bar)
    public View cProgressBar;

    @BindView(R.id.c_error)
    public View cError;

    @BindView(R.id.tv_error)
    public TextView tvError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.setView(this);
        presenter.start();
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
        // Refresh binding
        getBinding().setService(service);

        // Activity title
        if (service.getType() == Service.TYPE_WASH)
            setTitle(R.string.activity_service_details_wash);
        else if (service.getType() == Service.TYPE_REPAIR)
            setTitle(R.string.activity_service_details_repair);

        // Images slider
        for (Image image : service.getImages())
            sliderLayout.addSlider(
                    new DefaultSliderView(this)
                            .image(image.getUrl()));
        sliderLayout.setCustomIndicator(sliderIndicator);
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
    public Intent getIntentFromView() {
        return getIntent();
    }

    @Override
    public void createOrder() {
        presenter.createOrder();
    }

    @Override
    @OnClick(R.id.btn_reload)
    public void reload() {
        presenter.fetchService();
    }
}
