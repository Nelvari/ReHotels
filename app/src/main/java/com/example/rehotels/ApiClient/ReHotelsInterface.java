package com.example.rehotels.ApiClient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ReHotelsInterface {

    @FormUrlEncoded
    @POST("rehotels/login.php/")
    Call<User> login(@Field("username")String username, @Field("password")String password);

    @FormUrlEncoded
    @POST("rehotels/register.php/")
    Call<User> register(@Field("username")String username, @Field("password")String password, @Field("email")String email, @Field("notlp")String notlp);


}
