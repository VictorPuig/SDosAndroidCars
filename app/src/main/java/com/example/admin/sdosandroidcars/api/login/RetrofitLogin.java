package com.example.admin.sdosandroidcars.api.login;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitLogin {
    @POST("/login")
    Call<User> login(@Body User user);
}
