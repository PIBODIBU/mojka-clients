package com.mojka.poisk.ui.presenter;

import android.content.Intent;
import android.util.Log;

import com.mojka.poisk.R;
import com.mojka.poisk.data.api.APIGenerator;
import com.mojka.poisk.data.api.inrerfaces.ServiceAPI;
import com.mojka.poisk.data.callback.Callback;
import com.mojka.poisk.data.model.BaseDataWrapper;
import com.mojka.poisk.data.model.Service;
import com.mojka.poisk.ui.contract.ServiceDetailsContract;

import static com.mojka.poisk.ui.activity.ServiceDetailsActivity.INTENT_KEY_SERVICE_ID;

public class ServiceDetailsPresenterImpl implements ServiceDetailsContract.Presenter {
    private static final String TAG = "ServiceDetailsPres";

    private ServiceDetailsContract.View view;
    private Service service;

    @Override
    public void start() {
        if (!checkIntent(view.getIntentFromView()))
            return;

        fetchService();
    }

    @Override
    public void setView(ServiceDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public Boolean checkIntent(Intent intent) {
        if (intent == null || intent.getExtras() == null)
            return false;

        if (!intent.getExtras().containsKey(INTENT_KEY_SERVICE_ID))
            return false;

        return true;
    }

    @Override
    public void fetchService() {
        Integer serviceId = getServiceIdFromIntent(view.getIntentFromView());

        if (serviceId == null) {
            Log.e(TAG, "fetchService: service id is null");
            return;
        }

        view.setErrorText(-1);
        view.showProgressBar();

        APIGenerator.createService(ServiceAPI.class).getService(serviceId).enqueue(new Callback<BaseDataWrapper<Service>>() {
            @Override
            public void onSuccess(BaseDataWrapper<Service> response) {
                if (response != null && response.getError()) {
                    onError();
                    return;
                }

                setService(response.getResponseObj());
                view.setupUi(getService());
            }

            @Override
            public void onError() {
                view.setErrorText(R.string.error_loading_service_details);
            }

            @Override
            public void onDone() {
                view.hideProgressBar();
            }
        });
    }

    @Override
    public void createOrder() {

    }

    @Override
    public Integer getServiceIdFromIntent(Intent intent) {
        return view.getIntentFromView().getExtras().getInt(INTENT_KEY_SERVICE_ID, -1);
    }

    @Override
    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public Service getService() {
        return service;
    }
}
