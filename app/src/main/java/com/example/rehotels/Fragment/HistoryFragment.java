package com.example.rehotels.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.rehotels.Adapter.DetailAdapter;
import com.example.rehotels.Adapter.HistoryAdapter;
import com.example.rehotels.ApiClient.APIClient;
import com.example.rehotels.Model.DetailModel;
import com.example.rehotels.Model.HistoryModel;
import com.example.rehotels.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    RecyclerView recyclerView;
    
    private List<HistoryModel> historyModels;
    HistoryAdapter historyAdapter;
    SharedPreferences sharedPref;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.rvHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        sharedPref = this.getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        getHistory();

        return view;
    }

    private void getHistory() {
        historyModels = new ArrayList<>();
        AndroidNetworking.post(APIClient.BASE_URL + "rehotels/history.php")
                .addBodyParameter("userId", String.valueOf(sharedPref.getInt("userid", 0)))
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

                                HistoryModel modelHistory = new HistoryModel();
                                JSONObject object = data.getJSONObject(i);

                                modelHistory.setId(object.getInt("id"));
                                modelHistory.setNamaHotel(object.getString("namaHotel"));
                                modelHistory.setJenisRuang(object.getString("jenisRuang"));
                                modelHistory.setTanggalCheckIn(object.getString("tanggalCheckIn"));
                                modelHistory.setTanggalCheckOut(object.getString("tanggalCheckOut"));
                                modelHistory.setStatus(object.getString("status"));
                                historyModels.add(modelHistory);

                            }

                            historyAdapter = new HistoryAdapter(historyModels);
                            recyclerView.setAdapter(historyAdapter);

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
