package com.example.admin.sdosandroidcars;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class Login {

    public static final String TAG = "Login";
    public static final String LOGIN_URL = Constants.getUrlFor("login");

    String username, password;

    Login(String username, String password) {

        this.username = username;
        this.password = password;
    }

    public boolean doLogin() throws IOException {
        Log.d(TAG, "doLogin started");

        URL loginUrl = new URL(LOGIN_URL);

        HttpURLConnection conn = (HttpURLConnection) loginUrl.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setConnectTimeout(5000);
        //conn.setReadTimeout();
        conn.setDoInput(true);
        conn.setDoOutput(true);

        int responseCode;
        try {
            OutputStream os = conn.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            JSONObject outputJson = new JSONObject();
            outputJson.put("username", username);
            outputJson.put("password", password);

            Log.d(TAG, "json:" + outputJson.toString());

            bw.write(outputJson.toString());
            bw.flush();

            bw.close();

            responseCode = conn.getResponseCode();
        } catch (java.net.SocketTimeoutException e) {
            Log.e(TAG, "Timeout!");
            return false;
        } catch (JSONException e) {
            Log.e(TAG, "Error json?");
            return false;
        }

        if (responseCode == HttpURLConnection.HTTP_OK) {
            String line, response = "";

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((line = br.readLine()) != null) {
                Log.d(TAG, "Response line: " + line);
                response += line;
            }

            br.close();

            JSONObject inputJson = null;
            try {
                inputJson = new JSONObject(response);

                if (inputJson.getBoolean("success"))
                    return true;

            } catch (JSONException e) {
                Log.e(TAG, "JSON malformat o Resposta JSON no es la esperada!");
            }

        } else {
            Log.e(TAG, "Response code is not OK! (" + responseCode + ")");
        }

        return false;
    }
}
