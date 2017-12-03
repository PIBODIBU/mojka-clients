package com.mojka.poisk.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mojka.poisk.R;
import com.rey.material.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListHistoryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.root_view)
    public View rootView;

    @BindView(R.id.iv_cover)
    public ImageView ivCover;

    @BindView(R.id.tv_name)
    public TextView tvName;

    @BindView(R.id.tv_date)
    public TextView tvDate;

    public OrderListHistoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
