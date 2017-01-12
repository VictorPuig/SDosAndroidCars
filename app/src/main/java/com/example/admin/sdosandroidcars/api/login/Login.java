package com.example.admin.sdosandroidcars.api.login;

import android.util.Log;

import com.example.admin.sdosandroidcars.Constants;
import com.example.admin.sdosandroidcars.api.APICallbackListener;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Login implements APICallbackListener {

    public static final String TAG = "Login";
    private static final String LOGIN_URL = Constants.getUrlFor("login");

    private String username, password;
    private LoginResultListener loginResultListener;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /*public void doLogin() throws Exception {
        Log.d(TAG, "doLogin started");

        if (loginResultListener == null) {
            Log.d(TAG, "loginResultListener no establert");

            throw new Exception("loginResultListener no establert");
        }

        APICall loginCall = new APICall("POST", LOGIN_URL);

        JSONObject outputJson = new JSONObject();
        try {
            outputJson.put("username", username);
            outputJson.put("password", password);
        } catch (JSONException e) {
            Log.e(TAG, "Json error mentre es construia el l'objecte per enviar");
            e.printStackTrace();
        }

        loginCall.setRequestJson(outputJson);
        loginCall.setOnAPICallbackListener(this);
        loginCall.doAPICall();
    }*/

    public void doLogin() {
        Log.d(TAG, "DOLOGINS");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.getUrlFor(""))
                .build();

        RetrofitLogin retrofitLogin = retrofit.create(RetrofitLogin.class);

        Call<User> call = retrofitLogin.login(new User(username, password));

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(TAG, response.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "Retrofit failure");
            }
        });
    }

    public void setOnLoginResultListener(LoginResultListener lrs) {
        loginResultListener = lrs;
    }

    @Override
    public void onAPICallback(JSONObject json) {
        Log.d(TAG, ".onAPICallback() cridat");

        loginResultListener.onLoginResult(json);
    }
}
