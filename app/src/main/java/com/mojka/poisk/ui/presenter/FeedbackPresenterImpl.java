package com.mojka.poisk.ui.presenter;

import android.widget.ArrayAdapter;

import com.mojka.poisk.R;
import com.mojka.poisk.data.account.AccountService;
import com.mojka.poisk.data.api.APIGenerator;
import com.mojka.poisk.data.api.inrerfaces.FeedbackAPI;
import com.mojka.poisk.data.callback.Callback;
import com.mojka.poisk.data.model.BaseDataWrapper;
import com.mojka.poisk.data.model.BaseErrorResponse;
import com.mojka.poisk.data.model.FeedbackSubject;
import com.mojka.poisk.ui.contract.FeedbackContract;

import java.util.List;

public class FeedbackPresenterImpl implements FeedbackContract.Presenter {
    private static final String TAG = "FeedbackPresenterImpl";

    private FeedbackContract.View view;
    private ArrayAdapter<FeedbackSubject> adapter;

    @Override
    public void start() {
        view.setupUi();
    }

    @Override
    public void setView(FeedbackContract.View view) {
        this.view = view;
    }

    @Override
    public void fetchSubjects() {
        APIGenerator.createService(FeedbackAPI.class).getSubjects().enqueue(new Callback<BaseDataWrapper<List<FeedbackSubject>>>() {
            @Override
            public void onSuccess(BaseDataWrapper<List<FeedbackSubject>> response) {
                super.onSuccess(response);

                if (response.getError()) {
                    view.showToast(R.string.error);
                    return;
                }

                adapter = new ArrayAdapter<>(
                        view.getViewActivity(),
                        R.layout.support_simple_spinner_dropdown_item,
                        response.getResponseObj());

                view.setSpinnerAdapter(adapter);
            }
        });
    }

    @Override
    public ArrayAdapter<FeedbackSubject> getAdapter() {
        return adapter;
    }

    @Override
    public void send(String subject, String message, String email) {
        APIGenerator.createService(FeedbackAPI.class).send(
                new AccountService(view.getViewActivity()).getToken(),
                subject,
                email,
                message
        ).enqueue(new Callback<BaseErrorResponse>() {
            @Override
            public void onSuccess(BaseErrorResponse response) {
                super.onSuccess(response);

                if (response.getError()) {
                    onError();
                    return;
                }

                view.showToast(R.string.sent);
            }

            @Override
            public void onError() {
                view.showToast(R.string.error);
            }
        });
    }
}
