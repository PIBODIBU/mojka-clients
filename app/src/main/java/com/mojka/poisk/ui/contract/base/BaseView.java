package com.mojka.poisk.ui.contract.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

public interface BaseView {
    Context getViewContext();

    AppCompatActivity getActivity();

    void setupUi();
}