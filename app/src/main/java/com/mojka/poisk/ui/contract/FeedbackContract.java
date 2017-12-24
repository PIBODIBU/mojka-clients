package com.mojka.poisk.ui.contract;

import android.support.annotation.StringRes;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mojka.poisk.data.model.FeedbackSubject;
import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;

public interface FeedbackContract {
    interface View extends BaseView {
        void showToast(@StringRes int text);

        void onItemSelected(Spinner spinner, int index);

        void setSpinnerAdapter(ArrayAdapter<FeedbackSubject> adapter);

        void send();
    }

    interface Presenter extends BasePresenter<View> {
        void fetchSubjects();

        void send(String subject,
                  String message,
                  String email);

        ArrayAdapter<FeedbackSubject> getAdapter();
    }
}
