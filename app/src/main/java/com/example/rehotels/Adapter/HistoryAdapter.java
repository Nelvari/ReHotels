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

import com.example.rehotels.DetailActivity;
import com.example.rehotels.InfoHistoryActivity;
import com.example.rehotels.Model.HistoryModel;
import com.example.rehotels.Model.HomeModel;
import com.example.rehotels.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    private List<HistoryModel> dataList;
    View viewku;

    public HistoryAdapter(List<HistoryModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        viewku = layoutInflater.inflate(R.layout.list_history, parent, false);
        return new HistoryAdapter.ViewHolder(viewku);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryModel model = dataList.get(position);
        holder.tvTitle.setText("Reservasi " + model.getNamaHotel());
        holder.tvInfo.setText(model.getStatus());
        holder.tvJenisRuang.setText(model.getJenisRuang());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(holder.itemView.getContext(), InfoHistoryActivity.class);
                in.putExtra("model", model);
                holder.itemView.getContext().startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle, tvInfo, tvJenisRuang;
        RelativeLayout relativeLayout;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvInfo = itemView.findViewById(R.id.tvInfo);
            tvJenisRuang = itemView.findViewById(R.id.tvJenisRuang);
            relativeLayout = itemView.findViewById(R.id.historyRelative);
        }
    }

}
