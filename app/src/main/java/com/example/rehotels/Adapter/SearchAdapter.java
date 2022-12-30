package com.example.rehotels.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.rehotels.Model.HomeModel;
import com.example.rehotels.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends ArrayAdapter<HomeModel> {

    private List<HomeModel> homeModels;

    public SearchAdapter(@NonNull Context context, @NonNull List<HomeModel> homeModelList) {
        super(context, 0, homeModelList);
        homeModels = new ArrayList<>(homeModelList);
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false
            );
        }
        TextView tvTitle = convertView.findViewById(R.id.tvTitleList);
        TextView tvLocation = convertView.findViewById(R.id.tvLocation);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);
        ImageView ivStudio = convertView.findViewById(R.id.ivStudio);
        HomeModel homeModel = getItem(position);
        if (homeModel != null) {
            tvTitle.setText(homeModel.getNamaHotel());
            tvLocation.setText(homeModel.getAlamat());
            tvPrice.setText("Rp. " + homeModel.getHargaHotel() + ",-");
            Glide
                    .with(ivStudio.getContext())
                    .load(homeModel.getUrlImage())
                    .placeholder(R.mipmap.icon)
                    .into(ivStudio);
        }
        return convertView;
    }
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<HomeModel> suggestions = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(homeModels);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (HomeModel item : homeModels) {
                    if (item.getNamaHotel().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((HomeModel) resultValue).getNamaHotel();
        }
    };
}
