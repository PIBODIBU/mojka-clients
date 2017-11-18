package com.mojka.poisk.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mojka.poisk.R;
import com.mojka.poisk.data.model.City;
import com.mojka.poisk.ui.holder.SettingsCityViewHolder;

import java.util.LinkedList;

public class SettingsCityAdapter extends RecyclerView.Adapter<SettingsCityViewHolder> {
    private LinkedList<City> cities = new LinkedList<>();
    private OnItemClickListener onItemClickListener;

    public SettingsCityAdapter() {
    }

    @Override
    public SettingsCityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SettingsCityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_settings_city, parent, false));
    }

    @Override
    public void onBindViewHolder(SettingsCityViewHolder holder, int position) {
        if (cities.get(position) == null)
            return;

        final City city = cities.get(position);

        holder.tvName.setText(city.getName());

        if (onItemClickListener != null)
            holder.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(city);
                }
            });
    }

    public void addItem(City city) {
        this.cities.add(city);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(City city);
    }
}
