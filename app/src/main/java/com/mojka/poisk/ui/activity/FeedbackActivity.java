package com.mojka.poisk.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mojka.poisk.R;
import com.mojka.poisk.data.model.FeedbackSubject;
import com.mojka.poisk.ui.contract.FeedbackContract;
import com.mojka.poisk.ui.presenter.FeedbackPresenterImpl;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class FeedbackActivity extends BaseNavDrawerActivity implements FeedbackContract.View {
    private static final String TAG = "FeedbackActivity";

    @BindView(R.id.spinner)
    public Spinner spinner;

    @BindView(R.id.et_email)
    public EditText etEmail;

    @BindView(R.id.et_message)
    public EditText etMessage;

    private FeedbackContract.Presenter presenter = new FeedbackPresenterImpl();
    private String subject = "";

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
    }

    @Override
    public void setSpinnerAdapter(ArrayAdapter<FeedbackSubject> adapter) {
        spinner.setAdapter(adapter);
    }

    @Override
    public void showToast(int text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
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
    public void onItemSelected(Spinner spinner, int index) {
        subject = ((String) spinner.getItemAtPosition(index));
    }

    @Override
    @OnClick(R.id.btn_send)
    public void send() {
        presenter.send(
                subject,
                etMessage.getText().toString(),
                etEmail.getText().toString());
    }
}
