package com.mojka.poisk.ui.presenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;

import com.mojka.poisk.R;
import com.mojka.poisk.data.account.AccountService;
import com.mojka.poisk.data.api.APIGenerator;
import com.mojka.poisk.data.api.inrerfaces.OrderAPI;
import com.mojka.poisk.data.callback.Callback;
import com.mojka.poisk.data.model.BaseDataWrapper;
import com.mojka.poisk.data.model.BaseErrorResponse;
import com.mojka.poisk.data.model.Order;
import com.mojka.poisk.ui.activity.ServiceDetailsActivity;
import com.mojka.poisk.ui.adapter.OrderListActiveAdapter;
import com.mojka.poisk.ui.contract.OrderListContract;
import com.mojka.poisk.ui.fragment.OrderListActiveFragment;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class OrderListActivePresenterImpl implements OrderListContract.Active.Presenter {
    private OrderListContract.Active.View view;
    private OrderListActiveAdapter adapter;

    @Override
    public void start() {
        fetchOrders();
    }

    @Override
    public void setView(OrderListContract.Active.View view) {
        this.view = view;
    }

    @Override
    public OrderListActiveAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void setupAdapter(List<Order> orders) {
        adapter = new OrderListActiveAdapter(view.getViewContext(), orders);

        adapter.setActionListener(new OrderListActiveAdapter.ActionListener() {
            @Override
            public void onClick(Order order) {
                view.getViewActivity().startActivity(new Intent(view.getViewActivity(), ServiceDetailsActivity.class)
                        .putExtra(ServiceDetailsActivity.INTENT_KEY_SERVICE_ID, order.getService().getId()));
            }

            @Override
            public void onMoveOrder(final Order order) {
                view.showDateTimeChooser(dateTime -> APIGenerator.createService(OrderAPI.class).moveOrder(
                        order.getId(),
                        dateTime,
                        new AccountService(view.getViewContext()).getToken()).enqueue(new Callback<BaseErrorResponse>() {
                    @Override
                    public void onSuccess(BaseErrorResponse response) {
                        if (response.getError()) {
                            onError();
                            return;
                        }

                        view.showToast(R.string.order_move_success);
                        fetchOrders();
                    }

                    @Override
                    public void onError() {
                        view.showToast(R.string.order_move_error);
                    }
                }));
            }

            @Override
            public void onCancelOrder(final Order order) {
                new AlertDialog.Builder(view.getViewContext())
                        .setCancelable(false)
                        .setTitle(view.getViewActivity().getString(R.string.dialog_title_cancel_order))
                        .setMessage(view.getViewActivity().getString(R.string.dialog_message_cancel_order))
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                APIGenerator.createService(OrderAPI.class).cancelOrder(
                                        order.getId(),
                                        new AccountService(view.getViewContext()).getToken()
                                ).enqueue(new Callback<BaseErrorResponse>() {
                                    @Override
                                    public void onSuccess(BaseErrorResponse response) {
                                        if (response.getError()) {
                                            onError();
                                            return;
                                        }

                                        fetchOrders();
                                    }

                                    @Override
                                    public void onError() {
                                        view.showToast(R.string.error);
                                    }
                                });
                            }
                        }).setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel()).create().show();
            }
        });

        if (orders.size() == 0)
            view.showEmptyListAlert();
        else
            view.hideEmptyListAlert();
    }

    @Override
    public void fetchOrders() {
        view.showLoadingScreen();

        APIGenerator.createService(OrderAPI.class).getOrders(new AccountService(view.getViewContext()).getToken())
                .enqueue(new Callback<BaseDataWrapper<List<Order>>>() {
                    @Override
                    public void onSuccess(BaseDataWrapper<List<Order>> response) {
                        if (response.getError()) {
                            onError();
                            return;
                        }

                        List<Order> orders = new LinkedList<>();

                        for (Order order : response.getResponseObj())
                            if (!order.getDone())
                                orders.add(order);

                        sortOrders(orders);
                        setupAdapter(orders);
                        view.setupUi();
                    }

                    @Override
                    public void onError() {
                        view.showToast(R.string.error);
                    }

                    @Override
                    public void onDone() {
                        view.hideLoadingScreen();
                    }
                });
    }

    @Override
    public void sortOrders(List<Order> orders) {
        Collections.sort(orders, (order, t1) -> t1.getId().compareTo(order.getId()));
    }
}
