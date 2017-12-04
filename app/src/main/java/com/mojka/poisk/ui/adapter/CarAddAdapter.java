package com.mojka.poisk.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mojka.poisk.R;
import com.mojka.poisk.ui.holder.CarAddViewHolder;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class CarAddAdapter extends RecyclerView.Adapter<CarAddViewHolder> {
    private List<Uri> images = new LinkedList<>();
    private Context context;
    private OnDeleteClickListener onDeleteClickListener;

    public CarAddAdapter(Context context) {
        this.context = context;
    }

    @Override
    public CarAddViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CarAddViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_new, null, false));
    }

    @Override
    public void onBindViewHolder(CarAddViewHolder holder, int position) {
        final Uri image = images.get(position);

        if (image == null)
            return;

        Picasso.with(context)
                .load(image)
                .into(holder.ivPhoto);

        if (onDeleteClickListener != null)
            holder.ibDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClickListener.onDelete(image);
                }
            });
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
