package com.mojka.poisk.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.LinkedList;
import java.util.List;

public class OrderListPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments = new LinkedList<>();
    private List<String> titles = new LinkedList<>();

    public OrderListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public OrderListPagerAdapter addFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
        return this;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}