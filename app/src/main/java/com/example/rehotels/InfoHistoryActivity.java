package com.example.rehotels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rehotels.Model.HistoryModel;
import com.example.rehotels.Model.HomeModel;

public class InfoHistoryActivity extends AppCompatActivity {

    TextView tvInfoPesananTambahan, tvDetailPesananTambahan, tvDetailCheckTambahan;

    HistoryModel historyModel;
    CardView cvChat;
    ImageView ivBack;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_history);

        tvInfoPesananTambahan = findViewById(R.id.tvInfoPesananTambahan);
        tvDetailPesananTambahan = findViewById(R.id.tvDetailPesananTambahan);
        tvDetailCheckTambahan = findViewById(R.id.tvDetailCheckTambahan);
        cvChat = findViewById(R.id.cvChat);

        ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);

        Intent it = getIntent();
        historyModel = (HistoryModel) it.getSerializableExtra("model");

        tvInfoPesananTambahan.setText("Pesanan atas nama " + sharedPref.getString("username", "") + " sudah diterima oleh " + historyModel.getNamaHotel());
        tvDetailPesananTambahan.setText(historyModel.getJenisRuang());
        tvDetailCheckTambahan.setText("Check In : " + historyModel.getTanggalCheckIn() + "\nCheck Out :" + historyModel.getTanggalCheckOut());

        cvChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean installed = appInstalledOrNot("com.whatsapp");
                if (installed){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=6285291412003"));
                    startActivity(intent);
                }else {
                    Toast.makeText(InfoHistoryActivity.this, "Whats app not installed on your device", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean appInstalledOrNot(String url){
        PackageManager packageManager =getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){
            app_installed = false;
        }
        return app_installed;
    }

}