package com.example.rehotels.Adapter;

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
import com.example.rehotels.Model.DetailModel;
import com.example.rehotels.Model.HomeModel;
import com.example.rehotels.R;
import com.example.rehotels.ReservasiActivity;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {

    private List<DetailModel> dataList;
    View viewku;

    public DetailAdapter(List<DetailModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        viewku = layoutInflater.inflate(R.layout.list_detail, parent, false);
        return new DetailAdapter.ViewHolder(viewku);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetailModel model = dataList.get(position);
        holder.tvTitleDetail.setText(model.getJenisRuang());
        holder.tvDesc.setText(model.getDeskripsi());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(holder.itemView.getContext(), ReservasiActivity.class);
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

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitleDetail, tvDesc;
        private ImageView ivStudio;
        RelativeLayout relativeLayout;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitleDetail = itemView.findViewById(R.id.tvTitleDetail);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            relativeLayout = itemView.findViewById(R.id.Relative);
            ivStudio = itemView.findViewById(R.id.ivStudio);
        }
    }

}
