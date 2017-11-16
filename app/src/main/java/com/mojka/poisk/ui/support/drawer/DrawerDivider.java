package com.mojka.poisk.ui.support.drawer;

import android.annotation.SuppressLint;
import android.view.View;

import com.mikepenz.materialdrawer.model.AbstractDrawerItem;
import com.mikepenz.materialdrawer.model.BaseViewHolder;
import com.mojka.poisk.R;

import java.util.List;

public class DrawerDivider extends AbstractDrawerItem<DrawerDivider, DrawerDivider.ViewHolder> {
    @Override
    public DrawerDivider.ViewHolder getViewHolder(View v) {
        return new DrawerDivider.ViewHolder(v);
    }

    @SuppressLint("ResourceType")
    @Override
    public int getType() {
        return 98;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.drawer_divider;
    }

    @Override
    public void bindView(DrawerDivider.ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);

        //call the onPostBindView method to trigger post bind view actions (like the listener to modify the item if required)
        onPostBindView(this, holder.itemView);
    }

    public static class ViewHolder extends BaseViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }
}
