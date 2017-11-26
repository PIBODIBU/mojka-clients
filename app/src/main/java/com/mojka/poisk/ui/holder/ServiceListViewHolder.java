package com.mojka.poisk.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mojka.poisk.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.root_view)
    public View view;

    @BindView(R.id.tv_name)
    public TextView tvName;

    @BindView(R.id.tv_working_hours)
    public TextView tvWorkingHours;

    @BindView(R.id.tv_nearest_order)
    public TextView tvNearestOrder;

    @BindView(R.id.iv_image)
    public ImageView ivCover;

    public ServiceListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
