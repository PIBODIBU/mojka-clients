package com.mojka.poisk.ui.contract;

import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;

public interface FeedbackContract {
    interface View extends BaseView {
        void onItemSelected(int index);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
