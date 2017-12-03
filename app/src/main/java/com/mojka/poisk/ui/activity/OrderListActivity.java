package com.mojka.poisk.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.adapter.OrderListPagerAdapter;
import com.mojka.poisk.ui.contract.OrderListContract;
import com.mojka.poisk.ui.fragment.OrderListActiveFragment;
import com.mojka.poisk.ui.fragment.OrderListHistoryFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderListActivity extends BaseNavDrawerActivity implements OrderListContract.View {
    private static final String TAG = "OrderListActivity";

    public static final int PAGE_ORDER_ACTIVE = 0;
    public static final int PAGE_ORDER_HISTORY = 1;

    public static final String INTENT_KEY_PAGE = "INTENT_KEY_PAGE";

    @BindView(R.id.view_pager)
    public ViewPager viewPager;

    @BindView(R.id.tab_layout)
    public TabLayout tabLayout;

    private OrderListPagerAdapter pagerAdapter = new OrderListPagerAdapter(getSupportFragmentManager());
    private OrderListActiveFragment activeFragment = new OrderListActiveFragment();
    private OrderListHistoryFragment historyFragment = new OrderListHistoryFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupUi();
        checkIntent(getIntent());
    }

    @Override
    public void checkIntent(Intent intent) {
        if (intent == null || intent.getExtras() == null || !intent.getExtras().containsKey(INTENT_KEY_PAGE))
            return;

        switch (intent.getExtras().getInt(INTENT_KEY_PAGE)) {
            case PAGE_ORDER_ACTIVE:
                viewPager.setCurrentItem(PAGE_ORDER_ACTIVE);
                return;
            case PAGE_ORDER_HISTORY:
                viewPager.setCurrentItem(PAGE_ORDER_HISTORY);
                return;
            default:
                break;
        }
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

    @OnClick(R.id.btn_to_main_screen)
    public void toMainScreen() {
        startActivity(new Intent(OrderListActivity.this, ProfileActivity.class));
        finish();
    }

    @Override
    public void setupUi() {
        pagerAdapter.addFragment(activeFragment, getString(R.string.fragment_order_active));
        pagerAdapter.addFragment(historyFragment, getString(R.string.fragment_order_history));

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

        tabLayout.setupWithViewPager(viewPager);
    }
}
