package com.mrjeus.demofilecamerapermission;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<File> mImages;

    public ImageAdapter(Context mContext, List<File> mListImage) {
        this.mContext = mContext;
        this.mImages = mListImage;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.customer_image, null);
        return new ImageViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Glide.with(holder.mImageView.getContext())
                .load(mImages.get(position))
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mImages== null ? 0 : mImages.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
        }
    }
}
