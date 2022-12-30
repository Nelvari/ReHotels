package com.example.rehotels.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.rehotels.Adapter.HomeAdapter;
import com.example.rehotels.Adapter.SearchAdapter;
import com.example.rehotels.ApiClient.APIClient;
import com.example.rehotels.ApiClient.ReHotelsInterface;
import com.example.rehotels.DetailActivity;
import com.example.rehotels.Model.HomeModel;
import com.example.rehotels.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;

    AutoCompleteTextView editText;

    private List<HomeModel> homeModels;
    HomeAdapter homeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.rvListHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getHotel();
        editText = view.findViewById(R.id.actv);

        return view;
    }

    private void getHotel() {
        homeModels = new ArrayList<>();
        AndroidNetworking.get(APIClient.BASE_URL + "rehotels/getAll.php")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("PAYLOAD");
                            Log.e("test", "onResponse: " + data );
                            for (int i = 0; i < data.length(); i++) {

                                HomeModel modelHome = new HomeModel();
                                JSONObject object = data.getJSONObject(i);

                                modelHome.setId(object.getInt("id"));
                                modelHome.setNamaHotel(object.getString("namaHotel"));
                                modelHome.setHargaHotel(object.getString("hargaHotel"));
                                modelHome.setUrlImage(object.getString("urlImage"));
                                modelHome.setAlamat(object.getString("alamat"));
                                modelHome.setRating(object.getString("rating"));
                                homeModels.add(modelHome);

                            }

                            SearchAdapter adapter = new SearchAdapter(getContext(), homeModels);
                            editText.setAdapter(adapter);

                            homeAdapter = new HomeAdapter(homeModels);
                            recyclerView.setAdapter(homeAdapter);

                            editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent in = new Intent(getActivity(), DetailActivity.class);
                                    in.putExtra("model", homeModels.get(position));
                                    startActivity(in);
                                }
                            });

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
