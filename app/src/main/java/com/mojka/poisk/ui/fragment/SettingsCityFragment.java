package com.mojka.poisk.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.contract.SettingsCityContract;
import com.mojka.poisk.ui.presenter.SettingsCityPresenterImpl;

import butterknife.BindView;

public class SettingsCityFragment extends BaseFragment implements SettingsCityContract.View {
    private final String TAG = "SettingsCityFragment";

    @BindView(R.id.root_view)
    public View rootView;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    @BindView(R.id.c_progress)
    public View cProgressBar;

    @BindView(R.id.et_city)
    public EditText etCity;

    private SettingsCityContract.Presenter presenter = new SettingsCityPresenterImpl();

    @SuppressLint("MissingPermission")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        presenter.setView(this);
        presenter.start();

        hide();

        return view;
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_settings_city;
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
    public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewActivity()));
        recyclerView.setAdapter(presenter.getAdapter());
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getViewActivity(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setCity(String city) {
        etCity.setText(city);
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public Activity getViewActivity() {
        return getActivity();
    }

    @Override
    public void setupUi() {
        setupRecyclerView();
    }

    @Override
    public SettingsCityContract.View getMVPView() {
        return this;
    }

    @Override
    public void show() {
        rootView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        rootView.setVisibility(View.GONE);
    }

    @Override
    public Boolean isShowing() {
        return rootView.getVisibility() == View.VISIBLE;
    }
}
