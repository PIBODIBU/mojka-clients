package com.mojka.poisk.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.holder.CarEditViewHolder;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class CarEditAdapter extends RecyclerView.Adapter<CarEditViewHolder> {
    private static final String TAG = "CarEditAdapter";

    private List<Uri> images = new LinkedList<>();
    private Context context;
    private OnDeleteClickListener onDeleteClickListener;

    public CarEditAdapter(Context context) {
        this.context = context;
    }

    @Override
    public CarEditViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CarEditViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_new, null, false));
    }

    @Override
    public void onBindViewHolder(CarEditViewHolder holder, int position) {
        final Uri image = images.get(position);

        if (image == null)
            return;

        Picasso.with(context)
                .load(image)
                .into(holder.ivPhoto);

        if (onDeleteClickListener != null)
            holder.ibDelete.setOnClickListener(v -> onDeleteClickListener.onDelete(image));
    }

    public void addImage(Uri uri) {
        images.add(uri);
        notifyItemInserted(images.indexOf(uri));
    }

    public void removeImage(Uri uri) {
        int position = images.indexOf(uri);
        images.remove(uri);
        notifyItemRemoved(position);
    }

    public List<Uri> getImages() {
        return images;
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public interface OnDeleteClickListener {
        void onDelete(Uri uri);
    }
}
