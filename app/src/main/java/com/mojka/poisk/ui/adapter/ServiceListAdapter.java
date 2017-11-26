package com.mojka.poisk.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mojka.poisk.R;
import com.mojka.poisk.data.model.Service;
import com.mojka.poisk.ui.holder.ServiceListViewHolder;
import com.mojka.poisk.ui.support.AbstractClickListener;
import com.mojka.poisk.utils.DateUtils;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListViewHolder> {
    private Context context;
    private List<Service> services;
    private AbstractClickListener<Service> clickListener;

    public ServiceListAdapter(Context context, List<Service> services, AbstractClickListener<Service> clickListener) {
        this.context = context;
        this.services = services;
        this.clickListener = clickListener;
    }

    @Override
    public ServiceListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ServiceListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, null, false));
    }

    @Override
    public void onBindViewHolder(ServiceListViewHolder holder, int position) {
        final Service service = services.get(position);

        if (service == null)
            return;

        holder.tvName.setText(service.getName());
        holder.tvWorkingHours.setText(service.getWorkingHours());
        holder.tvNearestOrder.setText(DateUtils.millisToPattern(service.getNearestEntry(), DateUtils.PATTERN_DATE));

        if (service.getImages() != null && service.getImages().get(0) != null && service.getImages().get(0).getUrl() != null && service.getImages().get(0).getUrl() != "")
            Picasso.with(context)
                    .load(service.getImages().get(0).getUrl())
                    .into(holder.ivCover);

        if (clickListener != null)
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(service);
                }
            });
    }

    public List<Service> getServices() {
        return services;
    }

    @Override
    public int getItemCount() {
        if (services != null)
            return services.size();
        else
            return 0;
    }
}
