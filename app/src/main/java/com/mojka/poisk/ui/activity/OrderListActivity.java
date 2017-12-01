package com.mojka.poisk.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.adapter.OrderListPagerAdapter;
import com.mojka.poisk.ui.contract.OrderListContract;
import com.mojka.poisk.ui.fragment.OrderListActiveFragment;

import butterknife.BindView;

public class OrderListActivity extends BaseNavDrawerActivity implements OrderListContract.View {
    private static final String TAG = "OrderListActivity";

    private static final int PAGE_ORDER_ACTIVE = 0;
    private static final int PAGE_ORDER_HISTORY = 1;

    @BindView(R.id.view_pager)
    public ViewPager viewPager;

    private OrderListPagerAdapter pagerAdapter = new OrderListPagerAdapter(getSupportFragmentManager());
    private OrderListActiveFragment orderListActiveFragment = new OrderListActiveFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupUi();
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    String getActivityTitle() {
        return getString(R.string.activity_order_list);
    }

    @Override
    OnCloseButtonListener getOnCloseButtonListener() {
        return null;
    }

    @Override
    protected Boolean showCloseButton() {
        return false;
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public Activity getViewActivity() {
        return this;
    }

    @Override
    public void setupUi() {
        pagerAdapter.addFragment(orderListActiveFragment);

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
