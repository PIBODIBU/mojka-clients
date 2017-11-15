package com.mojka.poisk.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mojka.poisk.R;

import java.util.HashMap;

public abstract class BaseNavDrawerActivity extends BaseActivity {
    private static final String TAG = "BaseNavDrawerActivity";

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected Drawer drawer;

    private Toolbar toolbar;
    private HashMap<String, IDrawerItem> drawerItems = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupDrawer();
    }

    private void setupToolbar() {
        if (toolbar == null)
            if (findViewById(R.id.toolbar) != null && findViewById(R.id.toolbar) instanceof Toolbar)
                toolbar = findViewById(R.id.toolbar);
    }

    protected void setupDrawer() {
        setupToolbar();

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withSliderBackgroundColorRes(R.color.colorPrimary)
                .withHeader(R.layout.drawer_header)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(true)
                .build();

        drawer.addItems(
                new PrimaryDrawerItem()
                        .withName("Главное меню")
                        .withTextColorRes(R.color.white)
                        .withSelectedTextColorRes(R.color.colorAccent)
                        .withIcon(R.drawable.ic_home)
                        .withIconColorRes(R.color.white)
                        .withSelectedIconColorRes(R.color.colorAccent),
                new PrimaryDrawerItem()
                        .withName("Карта")
                        .withTextColorRes(R.color.white)
                        .withSelectedTextColorRes(R.color.colorAccent)
                        .withIcon(R.drawable.ic_home)
                        .withIconColorRes(R.color.white)
                        .withSelectedIconColorRes(R.color.colorAccent),
                new PrimaryDrawerItem()
                        .withName("Список")
                        .withTextColorRes(R.color.white)
                        .withSelectedTextColorRes(R.color.colorAccent)
                        .withIcon(R.drawable.ic_home)
                        .withIconColorRes(R.color.white)
                        .withSelectedIconColorRes(R.color.colorAccent),
                new PrimaryDrawerItem()
                        .withName("Мои записи")
                        .withTextColorRes(R.color.white)
                        .withSelectedTextColorRes(R.color.colorAccent)
                        .withIcon(R.drawable.ic_home)
                        .withIconColorRes(R.color.white)
                        .withSelectedIconColorRes(R.color.colorAccent)
        );

        setDrawerSelection();
    }

    private void setDrawerSelection() {
        try {
            drawer.setSelection(drawerItems.get(this.getClass().getName()), false);
        } catch (Exception ex) {
            ex.printStackTrace();
            drawer.setSelection(-1);
        }
    }
}