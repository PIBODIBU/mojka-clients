package com.mojka.poisk.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.contract.ServiceListContract;
import com.mojka.poisk.ui.presenter.ServiceListPresenterImpl;
import com.rey.material.widget.ProgressView;

import butterknife.BindView;

public class ServiceListActivity extends BaseNavDrawerActivity implements ServiceListContract.View {
    private static final String TAG = "ServiceListActivity";

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    public SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.progress_view)
    public ProgressView progressView;

    private ServiceListContract.Presenter presenter = new ServiceListPresenterImpl();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.setRecyclerView(recyclerView);
        presenter.setView(this);
        presenter.start();
    }

    @Override
    public void showToast(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_service_list;
    }

    @Override
    String getActivityTitle() {
        return getString(R.string.activity_service_list);
    }

    @Override
    OnCloseButtonListener getOnCloseButtonListener() {
        return null;
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
    public void showProgressBar() {
        progressView.setVisibility(View.VISIBLE);
    }

    @Override
    protected Boolean showCloseButton() {
        return false;
    }

    @Override
    public void hideProgressBar() {
        progressView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setRefreshing(Boolean refreshing) {
        swipeRefreshLayout.setRefreshing(refreshing);
    }

    @Override
    public void setupUi() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.fetchServices();
            }
        });
    }
}
