package com.mojka.poisk.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mojka.poisk.R;
import com.mojka.poisk.data.model.City;
import com.mojka.poisk.ui.holder.SettingsCityViewHolder;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SettingsCityAdapter extends RecyclerView.Adapter<SettingsCityViewHolder> {
    private static final String TAG = "SettingsCityAdapter";

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
            holder.tvName.setOnClickListener(v -> onItemClickListener.onClick(city));
    }

    public void add(City city) {
        this.cities.add(city);
        notifyItemInserted(cities.indexOf(city));
    }

    public void add(List<City> cities) {
        this.cities.addAll(cities);
        notifyDataSetChanged();
    }

    public void clearDataSet() {
        if (cities != null) {
            cities.clear();
            notifyDataSetChanged();
        }
    }

    public void filter(List<City> cities, String filter) {
        if (cities == null || this.cities == null)
            return;

        this.cities.clear();

        if (Objects.equals(filter, "")) {
            add(cities);
            return;
        }

        for (City city : cities)
            if (city.getName().toLowerCase().contains(filter.toLowerCase()))
                add(city);

        notifyDataSetChanged();
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
