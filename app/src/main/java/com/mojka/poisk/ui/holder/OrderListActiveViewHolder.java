package com.mojka.poisk.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mojka.poisk.R;
import com.rey.material.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListActiveViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.root_view)
    public View rootView;

    @BindView(R.id.iv_cover)
    public ImageView ivCover;

    @BindView(R.id.tv_name)
    public TextView tvName;

    @BindView(R.id.tv_date)
    public TextView tvDate;

    @BindView(R.id.btn_move_order)
    public Button btnModeOrder;

    @BindView(R.id.btn_cancel_order)
    public Button btnCancelOrder;

    public OrderListActiveViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
