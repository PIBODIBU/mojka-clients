package com.mojka.poisk.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.contract.OrderListContract;
import com.mojka.poisk.ui.presenter.OrderListHistoryPresenterImpl;

import butterknife.BindView;

public class OrderListHistoryFragment extends BaseFragment implements OrderListContract.History.View {
    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    @BindView(R.id.c_progress_bar)
    public View cProgressBar;

    @BindView(R.id.tv_empty_list)
    public TextView tvEmptyListAlert;

    private OrderListContract.History.Presenter presenter = new OrderListHistoryPresenterImpl();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        presenter.setView(this);
        presenter.start();

        return view;
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_order_list_history;
    }

    @Override
    public void showEmptyListAlert() {
        tvEmptyListAlert.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyListAlert() {
        tvEmptyListAlert.setVisibility(View.INVISIBLE);
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public Activity getViewActivity() {
        return getActivity();
    }

    @Override
    public void setupUi() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewActivity()));
        recyclerView.setAdapter(presenter.getAdapter());
    }

    @Override
    public void showLoadingScreen() {
        cProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToast(int stringId) {
        Toast.makeText(getViewActivity(), stringId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoadingScreen() {
        cProgressBar.setVisibility(View.INVISIBLE);
    }
}
