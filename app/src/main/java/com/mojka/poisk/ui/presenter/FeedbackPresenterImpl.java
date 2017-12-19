package com.mojka.poisk.ui.presenter;

import com.mojka.poisk.ui.contract.FeedbackContract;

public class FeedbackPresenterImpl implements FeedbackContract.Presenter {
    private static final String TAG = "FeedbackPresenterImpl";

    private FeedbackContract.View view;

    @Override
    public void start() {
        view.setupUi();
    }

    @Override
    public void setView(FeedbackContract.View view) {
        this.view = view;
    }
}
