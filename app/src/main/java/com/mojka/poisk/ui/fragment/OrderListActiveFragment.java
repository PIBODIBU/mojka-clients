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
import com.mojka.poisk.ui.presenter.OrderListActivePresenterImpl;
import com.takisoft.datetimepicker.DatePickerDialog;
import com.takisoft.datetimepicker.TimePickerDialog;
import com.takisoft.datetimepicker.widget.DatePicker;
import com.takisoft.datetimepicker.widget.TimePicker;

import java.util.Calendar;

import butterknife.BindView;

public class OrderListActiveFragment extends BaseFragment implements OrderListContract.Active.View {
    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    @BindView(R.id.c_progress_bar)
    public View cProgressBar;

    @BindView(R.id.tv_empty_list)
    public TextView tvEmptyListAlert;

    private OrderListContract.Active.Presenter presenter = new OrderListActivePresenterImpl();
    private Calendar newOrderDate = Calendar.getInstance();

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
        return R.layout.fragment_order_list_active;
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
    public void showDateTimeChooser(final OnDateTimeChooseListener onDateTimeChooseListener) {
        Calendar now = Calendar.getInstance();

        final TimePickerDialog timePickerDialog = new TimePickerDialog(getViewActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                newOrderDate.set(Calendar.HOUR_OF_DAY, view.getHour());
                newOrderDate.set(Calendar.MINUTE, view.getMinute());

                onDateTimeChooseListener.onChoose(newOrderDate.getTimeInMillis());
            }
        }, 0, 0, true);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(getViewActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                newOrderDate = Calendar.getInstance();

                newOrderDate.set(Calendar.DAY_OF_MONTH, view.getDayOfMonth());
                newOrderDate.set(Calendar.MONTH, view.getMonth());
                newOrderDate.set(Calendar.YEAR, view.getYear());

                timePickerDialog.show();
            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
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

    public interface OnDateTimeChooseListener {
        void onChoose(Long dateTime);
    }
}
