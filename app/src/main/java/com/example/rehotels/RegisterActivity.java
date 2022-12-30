package com.example.rehotels;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etPassword, etPhone;
    Button btnRegister;
    TextView tvLogin;
    private ProgressDialog dialog;

    SharedPreferences sharedPref;

    ReHotelsInterface reHotelsInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);
        btnRegister = findViewById(R.id.btnRegister);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        etPhone = findViewById(R.id.etPhone);
        tvLogin = findViewById(R.id.tvLogin);

        reHotelsInterface = APIClient.getClient().create(ReHotelsInterface.class);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = etEmail.getText().toString().trim();
                String mPassword = etPassword.getText().toString().trim();
                String mUsername = etUsername.getText().toString().trim();
                String mPhone = etPhone.getText().toString().trim();
                dialog = new ProgressDialog(RegisterActivity.this);

                if(TextUtils.isEmpty(mUsername)){
                    etUsername.setError("Username tidak boleh kosong.");
                    return;
                }
                if(TextUtils.isEmpty(mEmail)){
                    etEmail.setError("Email tidak boleh kosong.");
                    return;
                }
                if(TextUtils.isEmpty(mPassword)){
                    etPassword.setError("Password tidak boleh kosong.");
                    return;
                }
                if(mPassword.length() < 6){
                    etPassword.setError("Password minimal 6 karakter.");
                    return;
                }if(TextUtils.isEmpty(mPhone)){
                    etPhone.setError("Phone tidak boleh kosong.");
                    return;
                }else{
                    dialog.setTitle("Register process");
                    dialog.setMessage("Please wait...");
                    dialog.show();
                    dialog.setCancelable(false);

                    Call<User> register = reHotelsInterface.register(mUsername, mPassword, mEmail, mPhone);

                    register.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            String status = response.body().getSuccess();
                            Log.d("register", "onResponse: " + response.body().getUsername());

                            if (status.equalsIgnoreCase("2")) {

                                dialog.dismiss();

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();

                            }else if(status.equalsIgnoreCase("0")){
                                final DialogInterface.OnClickListener message =new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        if (i == DialogInterface.BUTTON_POSITIVE) {
                                            dialog.dismiss();
                                        }

                                    }

                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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

    }
}