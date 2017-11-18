package com.mojka.poisk.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mojka.poisk.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsCityViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.city_name)
    public TextView tvName;

    public SettingsCityViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
