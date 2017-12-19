package com.mojka.poisk.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.contract.FeedbackContract;
import com.mojka.poisk.ui.presenter.FeedbackPresenterImpl;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemSelected;

public class FeedbackActivity extends BaseNavDrawerActivity implements FeedbackContract.View {
    private static final String TAG = "FeedbackActivity";

    @BindView(R.id.spinner)
    public Spinner spinner;

    private FeedbackContract.Presenter presenter = new FeedbackPresenterImpl();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter.setView(this);
        presenter.start();
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
        List<String> subjects = new LinkedList<>();
        subjects.add("Subject #1");
        subjects.add("Subject #2");
        subjects.add("Subject #3");
        subjects.add("Subject #4");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, subjects);
        spinner.setAdapter(adapter);
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    String getActivityTitle() {
        return getString(R.string.activity_feedback);
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
    @OnItemSelected(R.id.spinner)
    public void onItemSelected(int index) {
//        String string = ((String) adapterView.getItemAtPosition(i));
    }
}
