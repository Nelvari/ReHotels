package com.example.rehotels.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rehotels.DetailActivity;
import com.example.rehotels.Model.HomeModel;
import com.example.rehotels.R;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>{

    private List<HomeModel> dataList;
    View viewku;

    public HomeAdapter(List<HomeModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        viewku = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new HomeViewHolder(viewku);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        HomeModel model = dataList.get(position);
        holder.tvTitle.setText(model.getNamaHotel());
        holder.tvLocation.setText(model.getAlamat());
        holder.tvPrice.setText("Rp. " + model.getHargaHotel() + ",-");
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(holder.itemView.getContext(), DetailActivity.class);
                in.putExtra("model", model);
                holder.itemView.getContext().startActivity(in);
            }
        });
        Glide
                .with(holder.ivStudio.getContext())
                .load(dataList.get(position).getUrlImage())
                .placeholder(R.mipmap.icon)
                .into(holder.ivStudio);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle, tvLocation, tvPrice;
        private ImageView ivStudio;
        RelativeLayout relativeLayout;

        HomeViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitleList);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            relativeLayout = itemView.findViewById(R.id.Relative);
            ivStudio = itemView.findViewById(R.id.ivStudio);
        }
    }

}
