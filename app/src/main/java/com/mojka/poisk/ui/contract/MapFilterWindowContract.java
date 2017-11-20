package com.mojka.poisk.ui.contract;

import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;

public class MapFilterWindowContract {
    public interface View extends BaseView {
        void show();

        void hide();

        Boolean isShowing();

        MapFilterWindowContract.View getMVPView();
    }

    public interface Presenter extends BasePresenter<View> {
    }
}
