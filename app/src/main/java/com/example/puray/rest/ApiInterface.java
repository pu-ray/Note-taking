package com.example.puray.rest;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("api/v2/register")
    Call<RegistrationResponse> registerUser(@Field("email")String email,@Field("password") String password);

    @FormUrlEncoded
    @POST("api/v2/login")
    Call<LoginResponse> loginUser(@Field("email")String email,@Field("password") String password);
}

