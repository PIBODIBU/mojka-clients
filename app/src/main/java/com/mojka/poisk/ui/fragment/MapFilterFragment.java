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
import com.rey.material.widget.Button;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import butterknife.BindView;
import butterknife.OnClick;

public class MapFilterFragment extends BaseFragment implements MapFilterWindowContract.View {
    @BindView(R.id.root_view)
    public View rootView;

    @BindView(R.id.range_bar)
    public RangeSeekBar<Integer> rangeSeekBar;

    private MapFilterWindowContract.Presenter presenter = new MapFilterWindowPresenterImpl();

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
    public MapFilterWindowContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void setAbsoluteMin(Integer min) {
        rangeSeekBar.setRangeValues(min, rangeSeekBar.getAbsoluteMaxValue());
    }

    @Override
    public void setAbsoluteMax(Integer max) {
        rangeSeekBar.setRangeValues(rangeSeekBar.getAbsoluteMinValue(), max);
    }

    @Override
    public Integer getMin() {
        return rangeSeekBar.getSelectedMinValue();
    }

    @Override
    public Integer getMax() {
        return rangeSeekBar.getSelectedMaxValue();
    }

    @Override
    @OnClick(R.id.btn_save)
    public void save() {
        presenter.save();
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
