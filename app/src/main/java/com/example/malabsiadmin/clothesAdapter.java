package com.example.malabsiadmin;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;

public class clothesAdapter extends RecyclerView.Adapter<clothesAdapter.ClothingViewHOlder> {
    private List<item> clothingList;

    public clothesAdapter(List<item> clothingList) {
        this.clothingList = clothingList;
    }

    @NonNull
    @Override
    public ClothingViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clothes_item, parent, false);
        return new ClothingViewHOlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClothingViewHOlder holder, int position) {
        item i = clothingList.get(position);
        holder.code.setText(i.getCode());
        holder.price.setText(i.getPrice());
        if(!i.getImage().isEmpty()) {
            Glide.with(holder.itemView).load(i.getImage()).into(holder.ImageView);
        }

    }

    @Override
    public int getItemCount() {
        return clothingList.size();
    }

    public class ClothingViewHOlder extends RecyclerView.ViewHolder {
        private TextView code,price;
        private ImageView ImageView;

        public ClothingViewHOlder(@NonNull View itemView) {
            super(itemView);

            code = itemView.findViewById(R.id.clothing_code_tv);
            price =itemView.findViewById(R.id.price);
            ImageView = itemView.findViewById(R.id.clothing_image);
        }
    }
}
