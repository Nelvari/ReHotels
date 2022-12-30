package com.example.rehotels.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.rehotels.AccountActivity;
import com.example.rehotels.LoginActivity;
import com.example.rehotels.MainActivity;
import com.example.rehotels.R;

public class ProfileFragment extends Fragment {

    RelativeLayout rvAccount, rvPrivacy, rvAbout;
    TextView tvLogout, tvUsername;
    SharedPreferences sharedPref;
    ImageView ivProfile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        rvAccount = view.findViewById(R.id.rvAccount);
        tvLogout = view.findViewById(R.id.tvLogout);
        tvUsername = view.findViewById(R.id.tvUsername);
        ivProfile = view.findViewById(R.id.ivProfile);

        sharedPref = this.getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        tvUsername.setText(sharedPref.getString("username", "Username"));

        Glide
                .with(this)
                .load(sharedPref.getString("fotoProfile", ""))
                .placeholder(R.mipmap.photo)
                .into(ivProfile);

        rvAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPref.getString("username", "")==null
                        ||sharedPref.getString("username","").equalsIgnoreCase("")
                        ||sharedPref.getString("username","")==""){

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);

                }else {
                    Intent intent = new Intent(getActivity(), AccountActivity.class);
                    startActivity(intent);
                }
            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogInterface.OnClickListener dialog =new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (i){
                            case DialogInterface.BUTTON_POSITIVE:

                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.clear();
                                editor.commit();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }

                    }

                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Apakah anda yakin ingin logout?").setPositiveButton("Ya", dialog)
                        .setTitle("Konfirmasi logout")
                        .setNegativeButton("Tidak", dialog).show();


            }
        });

        return view;
    }
}
