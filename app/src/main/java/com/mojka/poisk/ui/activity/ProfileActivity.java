package com.mojka.poisk.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.contract.ProfileContract;
import com.mojka.poisk.ui.fragment.ProfilePresenterImpl;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

public class ProfileActivity extends BaseNavDrawerActivity implements ProfileContract.View {
    private final String TAG = "ProfileActivity";

    @BindView(R.id.iv_background)
    public ImageView ivBackground;

    private ProfileContract.Presenter presenter = new ProfilePresenterImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupUi();
        presenter.setView(this);
        presenter.start();
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    String getActivityTitle() {
        return getString(R.string.activity_profile);
    }

    @Override
    OnCloseButtonListener getOnCloseButtonListener() {
        return new OnCloseButtonListener() {
            @Override
            public void onclick() {
                finish();
            }
        };
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
    protected Boolean attachBottomNavigation() {
        return true;
    }

    @Override
    public void setupUi() {
        Picasso.with(this)
                .load(R.drawable.img_profile_bg)
                .into(ivBackground);
    }
}
