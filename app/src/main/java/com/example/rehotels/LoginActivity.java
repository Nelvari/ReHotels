package com.example.rehotels;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rehotels.ApiClient.APIClient;
import com.example.rehotels.ApiClient.ReHotelsInterface;
import com.example.rehotels.ApiClient.User;
import com.example.rehotels.Fragment.MainFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvRegister;
    private ProgressDialog dialog;

    ReHotelsInterface reHotelsInterface;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvRegister = findViewById(R.id.tvRegister);
        reHotelsInterface = APIClient.getClient().create(ReHotelsInterface.class);

        sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUsername = etUsername.getText().toString().trim();
                String mPassword = etPassword.getText().toString().trim();
                dialog = new ProgressDialog(LoginActivity.this);

                if(TextUtils.isEmpty(mUsername)){
                    etUsername.setError("username tidak boleh kosong.");
                    return;
                }
                if(TextUtils.isEmpty(mPassword)){
                    etPassword.setError("Password tidak boleh kosong.");
                    return;
                }
                if(mPassword.length() < 6){
                    etPassword.setError("Password minimal 6 karakter.");
                    return;
                }else {
                    dialog.setTitle("Login process");
                    dialog.setMessage("Please wait...");
                    dialog.show();
                    dialog.setCancelable(false);

                    Call<User> login = reHotelsInterface.login(mUsername, mPassword);

                    login.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            String status = response.body().getSuccess();
                            Log.d("login", "onResponse: " + response.body().getUsername());

                            if (status.equalsIgnoreCase("2")) {

                                String email = response.body().getEmail();
                                String username = response.body().getUsername();
                                int id = response.body().getId();
                                String notlp = response.body().getNotlp();
                                String fotoProfile = response.body().getFotoProfile();

                                Log.e("testing", "onResponse: " + username);

                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("username", username);
                                editor.putString("notlp", notlp);
                                editor.putString("email", email);
                                editor.putInt("userid", id);
                                editor.putString("fotoProfile", fotoProfile);
                                editor.putString("password", etPassword.getText().toString());
                                editor.apply();

                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }

                                finish();

                            }else if(status.equalsIgnoreCase("1")){
                                final DialogInterface.OnClickListener message =new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        if (i == DialogInterface.BUTTON_POSITIVE) {
                                            dialog.dismiss();
                                        }

                                    }

                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(response.body().getMessage()).setPositiveButton("Oke", message)
                                        .show();
                            }else if(status.equalsIgnoreCase("0")){
                                final DialogInterface.OnClickListener message =new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        if (i == DialogInterface.BUTTON_POSITIVE) {
                                            dialog.dismiss();
                                        }

                                    }

                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(response.body().getMessage()).setPositiveButton("Oke", message)
                                        .show();
                            }

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.e("login", "onFailure: " + t.getMessage());
                            dialog.dismiss();
                        }
                    });

                }

            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

    }
}