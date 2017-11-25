package com.mojka.poisk.ui.contract;

import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;
import com.mojka.poisk.ui.presenter.MapFilterWindowPresenterImpl;

public class MapFilterWindowContract {
    public interface View extends BaseView {
        void show();

        void hide();

        Boolean isShowing();

        MapFilterWindowContract.View getMVPView();

        Integer getMin();

        Integer getMax();

        void save();

        Presenter getPresenter();

        void setAbsoluteMin(Integer min);

        void setAbsoluteMax(Integer max);
    }

    public interface Presenter extends BasePresenter<View> {
        void save();

        void setFilterListener(MapFilterWindowPresenterImpl.FilterListener filterListener);
    }
}
