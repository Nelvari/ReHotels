package com.example.rehotels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.example.rehotels.Adapter.DetailAdapter;
import com.example.rehotels.Adapter.HomeAdapter;
import com.example.rehotels.Adapter.SearchAdapter;
import com.example.rehotels.ApiClient.APIClient;
import com.example.rehotels.Model.DetailModel;
import com.example.rehotels.Model.HomeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    HomeModel homeModel;

    TextView tvJudul, tvLocation, tvPrice, tvRating;
    ImageView ivDetail, ivBack;
    RecyclerView recyclerView;

    private List<DetailModel> detailModels;
    DetailAdapter detailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvJudul = findViewById(R.id.tvJudul);
        tvLocation = findViewById(R.id.tvLocation);
        tvPrice = findViewById(R.id.tvPrice);
        tvRating = findViewById(R.id.tvRating);
        ivDetail = findViewById(R.id.ivDetail);

        Intent it = getIntent();
        homeModel = (HomeModel) it.getSerializableExtra("model");

        tvJudul.setText(homeModel.getNamaHotel());
        tvLocation.setText(homeModel.getAlamat());
        tvPrice.setText("Rp. "+ homeModel.getHargaHotel() + ",-");
        tvRating.setText(homeModel.getRating() + "/5");
        Glide.with(this).load(homeModel.getUrlImage()).into(ivDetail);

        ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.rvDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getDetail(homeModel.getId(), homeModel.getNamaHotel());

    }

    private void getDetail(int hotelId, String namaHotel) {
        detailModels = new ArrayList<>();
        AndroidNetworking.post(APIClient.BASE_URL + "rehotels/getDetail.php")
                .addBodyParameter("hotelId", String.valueOf(hotelId))
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("PAYLOAD");
                            Log.e("test", "onResponse: " + data );
                            for (int i = 0; i < data.length(); i++) {

                                DetailModel modelDetail = new DetailModel();
                                JSONObject object = data.getJSONObject(i);

                                modelDetail.setId(object.getInt("id"));
                                modelDetail.setHotelId(hotelId);
                                modelDetail.setUrlImage(object.getString("urlImage"));
                                modelDetail.setJenisRuang(object.getString("jenisRuang"));
                                modelDetail.setDeskripsi(object.getString("deskripsi"));
                                modelDetail.setNamaHotel(namaHotel);
                                detailModels.add(modelDetail);

                            }

                            detailAdapter = new DetailAdapter(detailModels);
                            recyclerView.setAdapter(detailAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {
                            // received error from server
                            // error.getErrorCode() - the error code from server
                            // error.getErrorBody() - the error body from server
                            // error.getErrorDetail() - just an error detail
                            Log.d("testing", "onError errorCode : " + anError.getErrorCode());
                            Log.d("testing", "onError errorBody : " + anError.getErrorBody());
                            Log.d("testing", "onError errorDetail : " + anError.getErrorDetail());
                            // get parsed error object (If ApiError is your class)
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d("testing", "onError errorDetail : " + anError.getErrorDetail());
                        }
                    }
                });
    }

}