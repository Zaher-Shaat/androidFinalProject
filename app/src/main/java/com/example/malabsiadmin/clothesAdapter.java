package com.example.malabsiadmin;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;

public class clothesAdapter extends RecyclerView.Adapter<clothesAdapter.ClothingViewHOlder> {
    private List<item> clothingList;
    private ClothesClickListner clothesClickListner;


    public clothesAdapter(List<item> clothingList) {
        this.clothingList = clothingList;
    }

    public clothesAdapter(List<item> clothingList, ClothesClickListner clothesClickListner) {
        this.clothingList = clothingList;
        this.clothesClickListner = clothesClickListner;
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

        holder.ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              clothesClickListner.onDeleteListiner(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return clothingList.size();
    }

    public static class ClothingViewHOlder extends RecyclerView.ViewHolder {
        private final TextView code;
        private final TextView price;
        private final ImageView ImageView;
        private final ImageButton ib;

        public ClothingViewHOlder(@NonNull View itemView) {
            super(itemView);

            code = itemView.findViewById(R.id.clothing_code_tv);
            price =itemView.findViewById(R.id.price);
            ImageView = itemView.findViewById(R.id.clothing_image);
            ib = itemView.findViewById(R.id.clothes_delete);
        }
    }
}
