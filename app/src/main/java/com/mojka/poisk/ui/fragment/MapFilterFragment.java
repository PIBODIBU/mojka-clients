package com.mojka.poisk.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.contract.MapFilterWindowContract;
import com.mojka.poisk.ui.presenter.MapFilterWindowPresenterImpl;

import butterknife.BindView;

public class MapFilterFragment extends BaseFragment implements MapFilterWindowContract.View {
    @BindView(R.id.root_view)
    public View rootView;

    private MapFilterWindowContract.Presenter presenter = new MapFilterWindowPresenterImpl();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        presenter.setView(this);
        presenter.start();

        return view;
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_map_filter;
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

    @Override
    public MapFilterWindowContract.View getMVPView() {
        return this;
    }
}
