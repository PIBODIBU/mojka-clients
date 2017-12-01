package com.mojka.poisk.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mojka.poisk.R;
import com.mojka.poisk.data.model.Order;
import com.mojka.poisk.ui.holder.OrderListActiveViewHolder;
import com.mojka.poisk.utils.DateUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderListActiveAdapter extends RecyclerView.Adapter<OrderListActiveViewHolder> {
    private Context context;
    private List<Order> orders;

    public OrderListActiveAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @Override
    public OrderListActiveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderListActiveViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_active, null, false));
    }

    @Override
    public void onBindViewHolder(OrderListActiveViewHolder holder, int position) {
        Order order = orders.get(position);

        if (order == null)
            return;

        if (order.getService().getImages() != null && order.getService().getImages().get(0) != null && order.getService().getImages().get(0).getUrl() != "")
            Picasso.with(context)
                    .load(order.getService().getImages().get(0).getUrl())
                    .into(holder.ivCover);

        holder.tvName.setText(order.getService().getName());
        holder.tvDate.setText(DateUtils.millisToPattern(order.getDate(), DateUtils.PATTERN_DATE_TIME_WITH_BRACKETS));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
