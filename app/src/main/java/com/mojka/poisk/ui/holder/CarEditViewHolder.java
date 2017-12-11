package com.mojka.poisk.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.mojka.poisk.R;
import com.rey.material.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarEditViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_cover)
    public ImageView ivPhoto;

    @BindView(R.id.ib_delete)
    public ImageButton ibDelete;

    public CarEditViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
