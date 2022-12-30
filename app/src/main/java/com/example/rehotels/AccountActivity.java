package com.example.rehotels;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.example.rehotels.ApiClient.APIClient;
import com.example.rehotels.ApiClient.ReHotelsInterface;
import com.example.rehotels.Fragment.MainFragment;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {

    ImageView ivBack, ivProfile;
    Button btnProfile, btnUpdate;
    private String selectedImagePathfoto = "";
    private File selectedImageFilefoto;
    String foto="";
    String url;
    SharedPreferences sharedPref;
    EditText etUsername, etEmail, etPassword, etPhone;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        ivBack = findViewById(R.id.ivBack);
        ivProfile = findViewById(R.id.ivProfile);
        btnProfile = findViewById(R.id.btnProfile);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        btnUpdate = findViewById(R.id.btnUpdate);

        sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);

        etUsername.setText(sharedPref.getString("username", ""));
        etEmail.setText(sharedPref.getString("email", ""));
        etPassword.setText(sharedPref.getString("password", ""));
        etPhone.setText(sharedPref.getString("notlp", ""));

        url = sharedPref.getString("fotoProfile", "");

        dialog = new ProgressDialog(AccountActivity.this);

        Glide
                .with(this)
                .load(sharedPref.getString("fotoProfile", ""))
                .placeholder(R.mipmap.photo)
                .into(ivProfile);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foto="true";
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                if (r.getError() == null & foto.equalsIgnoreCase("true")) {

                                    selectedImagePathfoto=r.getPath();

                                    selectedImageFilefoto = new File(selectedImagePathfoto);
                                    Log.e("testing", "onCreate: " + selectedImagePathfoto);
                                    foto = "";


                                    AndroidNetworking.upload(APIClient.BASE_URL + "rehotels/image.php")
                                            .addMultipartFile("avatar", selectedImageFilefoto)
                                            .setPriority(Priority.MEDIUM)
                                            .build()
                                            .getAsJSONObject(new JSONObjectRequestListener() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    String message = response.optString("message");
                                                    url = response.optString("url");
                                                    Log.d("testing", "url: " + url);
                                                    Toast.makeText(AccountActivity.this, url + message, Toast.LENGTH_LONG).show();
                                                    Glide.with(AccountActivity.this).load(url).into(ivProfile);
                                                }

                                                @Override
                                                public void onError(ANError anError) {
                                                    Log.e("testing", "onError: " + anError.getMessage() );
                                                }
                                            });

                                }
                            }
                        })
                        .show(getSupportFragmentManager());
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setTitle("Update process");
                dialog.setMessage("Please wait...");
                dialog.show();
                dialog.setCancelable(false);
                AndroidNetworking.post(APIClient.BASE_URL + "rehotels/updateProfile.php")
                        .addBodyParameter("id", String.valueOf(sharedPref.getInt("userid", 0)))
                        .addBodyParameter("username", etUsername.getText().toString())
                        .addBodyParameter("password", etPassword.getText().toString())
                        .addBodyParameter("email", etEmail.getText().toString())
                        .addBodyParameter("notlp", etPhone.getText().toString())
                        .addBodyParameter("fotoProfile", url)
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("fotoProfile", url);
                                editor.putString("username", etUsername.getText().toString());
                                editor.putString("notlp", etPhone.getText().toString());
                                editor.putString("email", etEmail.getText().toString());
                                editor.putInt("userid", sharedPref.getInt("userid", 0));
                                editor.putString("password", etPassword.getText().toString());
                                editor.apply();

                                dialog.dismiss();

                                Intent intent = new Intent(AccountActivity.this, MainFragment.class);
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
        });

    }



}