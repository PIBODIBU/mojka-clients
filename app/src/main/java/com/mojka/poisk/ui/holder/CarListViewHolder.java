package com.mojka.poisk.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mojka.poisk.R;
import com.rey.material.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.root_view)
    public View rootView;

    @BindView(R.id.iv_cover)
    public ImageView ivCover;

    @BindView(R.id.tv_name)
    public TextView tvName;

    @BindView(R.id.tv_car_numbers)
    public TextView tvCarNumbers;

    @BindView(R.id.ib_edit)
    public Button btnEdit;

    @BindView(R.id.ib_delete)
    public Button btnDelete;

    public CarListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
