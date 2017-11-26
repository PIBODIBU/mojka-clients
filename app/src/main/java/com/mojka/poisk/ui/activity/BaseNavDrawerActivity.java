package com.mojka.poisk.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mojka.poisk.R;
import com.mojka.poisk.data.account.AccountService;
import com.mojka.poisk.ui.support.drawer.DrawerDivider;
import com.mojka.poisk.ui.support.drawer.DrawerItem;

import java.util.HashMap;

import butterknife.BindView;

public abstract class BaseNavDrawerActivity extends BaseActivity {
    private static final String TAG = "BaseNavDrawerActivity";

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected Drawer drawer;
    private HashMap<String, IDrawerItem> drawerItems = new HashMap<>();
    private AccountService accountService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountService = new AccountService(this);
        setupDrawer();
    }

    private void setupToolbar() {
        if (this.toolbar != null) {
            ImageButton ibMenu = toolbar.findViewById(R.id.ib_menu);

            if (ibMenu == null)
                return;

            ibMenu.setVisibility(View.VISIBLE);
            ibMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.openDrawer();
                }
            });
        }
    }

    protected void setupDrawer() {
        setupToolbar();

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withSliderBackgroundColorRes(R.color.colorPrimary)
                .withHeader(R.layout.drawer_header)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(true)
                .build();

        if (accountService.isLogged())
            ((TextView) drawer.getHeader().findViewById(R.id.tv_name)).setText(accountService.getAccount().getName());

        addDrawerItems();
        setDrawerSelection();
    }

    private void addDrawerItems() {
        IDrawerItem itemHome = new DrawerItem()
                .withIconRes(R.drawable.ic_home_white)
                .withIconSelectedRes(R.drawable.ic_home_white)
                .withOnDrawerItemClickListener(new DrawerItem.OnDrawerItemClickListener() {
                    @Override
                    public void onClick() {
                        startActivity(new Intent(BaseNavDrawerActivity.this, ProfileActivity.class));
                        finish();
                    }
                })
                .withTitle("Главное меню");
        IDrawerItem itemMap = new DrawerItem()
                .withIconRes(R.drawable.ic_map_white)
                .withIconSelectedRes(R.drawable.ic_map_accent)
                .withOnDrawerItemClickListener(new DrawerItem.OnDrawerItemClickListener() {
                    @Override
                    public void onClick() {
                        startActivity(new Intent(BaseNavDrawerActivity.this, MapActivity.class));
                        finish();
                    }
                })
                .withTitle("Карта");

        IDrawerItem itemList = new DrawerItem()
                .withIconRes(R.drawable.ic_list_white)
                .withIconSelectedRes(R.drawable.ic_list_accent)
                .withOnDrawerItemClickListener(new DrawerItem.OnDrawerItemClickListener() {
                    @Override
                    public void onClick() {
                        startActivity(new Intent(BaseNavDrawerActivity.this, ProfileActivity.class));
                        finish();
                    }
                })
                .withTitle("Список");

        IDrawerItem itemOrders = new DrawerItem()
                .withIconRes(R.drawable.ic_profile_white)
                .withIconSelectedRes(R.drawable.ic_profile_accent)
                .withOnDrawerItemClickListener(new DrawerItem.OnDrawerItemClickListener() {
                    @Override
                    public void onClick() {
                        drawer.closeDrawer();
                    }
                })
                .withTitle("Мои записи");

        IDrawerItem itemExit = new DrawerItem()
                .withIconRes(R.drawable.ic_close_white)
                .withOnDrawerItemClickListener(new DrawerItem.OnDrawerItemClickListener() {
                    @Override
                    public void onClick() {
                        accountService.logout();
                        startActivity(new Intent(BaseNavDrawerActivity.this, LoginActivity.class));
                        finish();
                    }
                })
                .withTitle("Выход");

        IDrawerItem itemLogin = new DrawerItem()
                .withIconRes(R.drawable.ic_profile_white)
                .withOnDrawerItemClickListener(new DrawerItem.OnDrawerItemClickListener() {
                    @Override
                    public void onClick() {
                        startActivity(new Intent(BaseNavDrawerActivity.this, LoginActivity.class));
                        finish();
                    }
                })
                .withTitle("Авторизация");

        drawer.addItem(itemHome);
        drawerItems.put("1", itemHome);

        drawer.addItem(itemMap);
        drawerItems.put(MapActivity.class.getName(), itemMap);

        drawer.addItem(itemList);
        drawerItems.put("3", itemList);

        drawer.addItem(itemOrders);
        drawerItems.put(ProfileActivity.class.getName(), itemOrders);

        drawer.addItem(new DrawerDivider());

        if (accountService.isLogged()) {
            drawer.addItem(itemExit);
            drawerItems.put("Exit", itemExit);
        }

        if (!accountService.isLogged()) {
            drawer.addItem(itemLogin);
            drawerItems.put("Auth", itemLogin);
        }
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