package com.mojka.poisk.ui.presenter;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.mojka.poisk.R;
import com.mojka.poisk.data.api.APIGenerator;
import com.mojka.poisk.data.api.inrerfaces.ServiceAPI;
import com.mojka.poisk.data.callback.Callback;
import com.mojka.poisk.data.model.BaseDataWrapper;
import com.mojka.poisk.data.model.Service;
import com.mojka.poisk.ui.activity.ServiceDetailsActivity;
import com.mojka.poisk.ui.adapter.ServiceListAdapter;
import com.mojka.poisk.ui.contract.ServiceListContract;
import com.mojka.poisk.ui.support.AbstractClickListener;

import java.util.LinkedList;
import java.util.List;

public class ServiceListPresenterImpl implements ServiceListContract.Presenter, AbstractClickListener<Service> {
    private ServiceListContract.View view;
    private RecyclerView recyclerView;
    private ServiceListAdapter adapter;

    @Override
    public void start() {
        view.setupUi();
        view.showProgressBar();

        setupRecyclerView();
        fetchServices();
    }

    @Override
    public void setView(ServiceListContract.View view) {
        this.view = view;
    }

    @Override
    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public void setupRecyclerView() {
        adapter = new ServiceListAdapter(view.getViewActivity(), new LinkedList<Service>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getViewActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(Service model) {
        view.getViewActivity().startActivity(new Intent(view.getViewActivity(), ServiceDetailsActivity.class)
                .putExtra(ServiceDetailsActivity.INTENT_KEY_SERVICE_ID, model.getId()));
    }

    @Override
    public void fetchServices() {
        APIGenerator.createService(ServiceAPI.class).getAllServices().enqueue(new Callback<BaseDataWrapper<List<Service>>>() {
            @Override
            public void onSuccess(BaseDataWrapper<List<Service>> response) {
                if (response.getError()) {
                    view.showToast(R.string.error_fetching_services);
                    return;
                }

                adapter.getServices().clear();
                adapter.getServices().addAll(response.getResponseObj());
                adapter.notifyDataSetChanged();

                view.setRefreshing(false);
            }

            @Override
            public void onError() {
                view.showToast(R.string.error_fetching_services);
            }

            @Override
            public void onDone() {
                view.hideProgressBar();
            }
        });
    }
}
