package com.example.rehotels;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.rehotels.ApiClient.APIClient;
import com.example.rehotels.Fragment.MainFragment;
import com.example.rehotels.Model.DetailModel;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReservasiActivity extends AppCompatActivity {

    ImageView ivDateTime1, ivDateTime2;
    TextView tvDateTime1, tvDateTime2, tvToolbar, tvJenisRuang;
    Button btnReservasi;
    ImageView ivBack;

    DetailModel detailModel;
    SharedPreferences sharedPref;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservasi);

        ivDateTime1 = findViewById(R.id.ivDateTime1);
        ivDateTime2 = findViewById(R.id.ivDateTime2);
        tvDateTime1 = findViewById(R.id.tvDateTime1);
        tvDateTime2 = findViewById(R.id.tvDateTime2);
        tvToolbar = findViewById(R.id.tvToolbar);
        tvJenisRuang = findViewById(R.id.tvJenisRuang);
        btnReservasi = findViewById(R.id.btnReservasi);
        ivBack = findViewById(R.id.ivBack);

        sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent it = getIntent();
        detailModel = (DetailModel) it.getSerializableExtra("model");

        tvToolbar.setText(detailModel.getNamaHotel());
        tvJenisRuang.setText(detailModel.getJenisRuang());

        dialog = new ProgressDialog(ReservasiActivity.this);

        ivDateTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                calendar.set(Calendar.MINUTE,minute);

                                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm  EEE, d MMM, yyyy");

                                tvDateTime1.setText(simpleDateFormat.format(calendar.getTime()));

                            }
                        };

                        new TimePickerDialog(ReservasiActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();

                    }
                };

                new DatePickerDialog(ReservasiActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        tvDateTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                calendar.set(Calendar.MINUTE,minute);

                                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm  EEE, d MMM, yyyy");

                                tvDateTime1.setText(simpleDateFormat.format(calendar.getTime()));

                            }
                        };

                        new TimePickerDialog(ReservasiActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();

                    }
                };

                new DatePickerDialog(ReservasiActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        ivDateTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                calendar.set(Calendar.MINUTE,minute);

                                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm  EEE, d MMM, yyyy");

                                tvDateTime2.setText(simpleDateFormat.format(calendar.getTime()));

                            }
                        };

                        new TimePickerDialog(ReservasiActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();

                    }
                };

                new DatePickerDialog(ReservasiActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        tvDateTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                calendar.set(Calendar.MINUTE,minute);

                                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm  EEE, d MMM, yyyy");

                                tvDateTime2.setText(simpleDateFormat.format(calendar.getTime()));

                            }
                        };

                        new TimePickerDialog(ReservasiActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();

                    }
                };

                new DatePickerDialog(ReservasiActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        btnReservasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPref.getString("username", "")==null
                        ||sharedPref.getString("username","").equalsIgnoreCase("")
                        ||sharedPref.getString("username","")==""){

                    Intent intent = new Intent(ReservasiActivity.this, LoginActivity.class);
                    startActivity(intent);

                }else {
                    String tanggalCheckIn = tvDateTime1.getText().toString();
                    String tanggalCheckOut = tvDateTime2.getText().toString();
                    dialog.setTitle("Update process");
                    dialog.setMessage("Please wait...");
                    dialog.show();
                    dialog.setCancelable(false);
                    AndroidNetworking.post(APIClient.BASE_URL + "rehotels/reservasi.php")
                            .addBodyParameter("userId", String.valueOf(sharedPref.getInt("userid", 0)))
                            .addBodyParameter("hotelId", String.valueOf(detailModel.getHotelId()))
                            .addBodyParameter("detailId", String.valueOf(detailModel.getId()))
                            .addBodyParameter("tanggalCheckIn", tanggalCheckIn)
                            .addBodyParameter("tanggalCheckOut", tanggalCheckOut)
                            .addBodyParameter("status", "Accept")
                            .setTag("test")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    dialog.dismiss();

                                    Intent intent = new Intent(ReservasiActivity.this, MainFragment.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();

                                }

                                @Override
                                public void onError(ANError anError) {
                                    Log.e("testing", "onError: " + anError.getErrorDetail());
                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                }
                            });
                }
            }
        });

    }
}