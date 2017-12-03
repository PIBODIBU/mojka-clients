package com.mojka.poisk.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mojka.poisk.R;
import com.mojka.poisk.data.model.Car;
import com.mojka.poisk.ui.holder.CarListViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarListAdapter extends RecyclerView.Adapter<CarListViewHolder> {
    private static final String TAG = "CarListAdapter";

    private List<Car> cars;
    private Context context;

    public CarListAdapter(List<Car> cars, Context context) {
        this.cars = cars;
        this.context = context;
    }

    @Override
    public CarListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CarListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, null, false));
    }

    @Override
    public void onBindViewHolder(CarListViewHolder holder, int position) {
        Car car = cars.get(position);

        if (car == null) {
            Log.e(TAG, "onBindViewHolder: car model is null");
            return;
        }

        if (car.getImages() != null && car.getImages().get(0) != null && car.getImages().get(0).getUrl() != "") {
            holder.ivCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(context)
                    .load(car.getImages().get(0).getUrl())
                    .into(holder.ivCover);
        } else {
            holder.ivCover.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Picasso.with(context)
                    .load(R.drawable.ic_no_picture)
                    .into(holder.ivCover);
        }

        holder.tvName.setText(car.getName());
        holder.tvCarNumbers.setText(car.getNumbers());

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }
}
