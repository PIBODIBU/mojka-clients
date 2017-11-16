package com.mojka.poisk.ui.support.drawer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.model.AbstractDrawerItem;
import com.mikepenz.materialdrawer.model.BaseViewHolder;
import com.mojka.poisk.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DrawerItem extends AbstractDrawerItem<DrawerItem, DrawerItem.ViewHolder> {
    private int icon;
    private int iconSelected;
    private String title;
    private OnDrawerItemClickListener onDrawerItemClickListener;

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceType")
    @Override
    public int getType() {
        return 99;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.drawer_item;
    }

    public DrawerItem withIconRes(@DrawableRes int icon) {
        this.icon = icon;
        return this;
    }

    public DrawerItem withIconSelectedRes(@DrawableRes int icon) {
        this.iconSelected = icon;
        return this;
    }

    public DrawerItem withOnDrawerItemClickListener(OnDrawerItemClickListener clickListener) {
        this.onDrawerItemClickListener = clickListener;
        return this;
    }

    public DrawerItem withTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public void bindView(ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);

        Context context = holder.itemView.getContext();

        holder.tvTitle.setText(title);
        if (onDrawerItemClickListener != null)
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDrawerItemClickListener.onClick();
                }
            });

        if (isSelected()) {
            holder.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            Picasso.with(context)
                    .load(iconSelected)
                    .into(holder.ivIcon);
        } else {
            holder.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.white));
            Picasso.with(context)
                    .load(icon)
                    .into(holder.ivIcon);
        }

        //call the onPostBindView method to trigger post bind view actions (like the listener to modify the item if required)
        onPostBindView(this, holder.itemView);
    }

    public interface OnDrawerItemClickListener {
        void onClick();
    }

    public static class ViewHolder extends BaseViewHolder {
        private ImageView ivIcon;
        private TextView tvTitle;
        private View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view.findViewById(R.id.root_view);
            ivIcon = view.findViewById(R.id.iv_icon);
            tvTitle = view.findViewById(R.id.tv_title);
        }
    }
}
