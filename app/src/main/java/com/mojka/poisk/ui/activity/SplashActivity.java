package com.mojka.poisk.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.contract.SplashContract;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements SplashContract.View {
    @BindView(R.id.iv_logo)
    public ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    startNextActivity();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void startNextActivity() {
        SplashActivity.this.startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void setupUi() {
        Picasso.with(this)
                .load(R.drawable.ic_logo)
                .into(ivLogo);
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    String getActivityTitle() {
        return null;
    }

    @Override
    OnCloseButtonListener getOnCloseButtonListener() {
        return null;
    }
}