package com.mojka.poisk.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.mojka.poisk.R;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        fetchToolbar();
    }

    private void fetchToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        if (toolbar == null)
            return;

        setupToolbar(toolbar);
    }

    private void setupToolbar(Toolbar toolbar) {
        TextView tvTitle = toolbar.findViewById(R.id.tv_title);
        tvTitle.setText(getActivityTitle());

        ImageButton ibClose = toolbar.findViewById(R.id.ib_close);
        if (getOnCloseButtonListener() != null)
            ibClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getOnCloseButtonListener().onclick();
                }
            });
    }

    abstract int getLayoutId();

    abstract String getActivityTitle();

    abstract OnCloseButtonListener getOnCloseButtonListener();

    interface OnCloseButtonListener {
        void onclick();
    }
}
