package com.mojka.poisk.ui.presenter;

import com.mojka.poisk.ui.contract.MapFilterWindowContract;

public class MapFilterWindowPresenterImpl implements MapFilterWindowContract.Presenter {
    private MapFilterWindowContract.View view;
    private FilterListener filterListener;

    @Override
    public void start() {
        view.setupUi();
    }

    @Override
    public void setView(MapFilterWindowContract.View view) {
        this.view = view;
    }

    @Override
    public void save() {
        if (filterListener != null)
            filterListener.onSave(view.getMin(), view.getMax());
    }

    @Override
    public void setFilterListener(FilterListener filterListener) {
        this.filterListener = filterListener;
    }

    public interface FilterListener {
        void onSave(Integer min, Integer max);
    }
}
