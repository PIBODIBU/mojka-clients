package com.mojka.poisk.ui.contract;

import android.widget.CompoundButton;

import com.mojka.poisk.data.model.ServiceType;
import com.mojka.poisk.ui.contract.base.BasePresenter;
import com.mojka.poisk.ui.contract.base.BaseView;

public interface MapFilterContract {
    interface View extends BaseView {
        void applyFilters();

        void onCheckBoxChanged(CompoundButton checkBox, boolean checked);
    }

    interface Presenter extends BasePresenter<View> {
        void addServiceToFilter(ServiceType serviceType);

        void removeServiceFromFilter(ServiceType serviceType);

        void apply();
    }
}
