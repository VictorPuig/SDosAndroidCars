package com.example.admin.sdosandroidcars.api;


import android.os.AsyncTask;
import android.util.Log;

import com.example.admin.sdosandroidcars.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class APICall {

    public static final String TAG = "APICall";

    private String method;
    private String url;
    private JSONObject requestJson;
    APICallbackListener callbackClass;

    public APICall(String method, String url) {
        this.url = url;
        this.method = method;
    }

    public APICall(String url) {
        this("GET", url);
    }

    private class APICallAsyncTask extends AsyncTask<Void, Void, JSONObject> {

        private final String AsyncTAG = "AsyncTask/" + TAG;

        @Override
        protected JSONObject doInBackground(Void... voids) {
            Log.d(AsyncTAG, ".doInBackground() cridat");

            Log.d(TAG, "method:" + method + " url:" + url);

            HttpURLConnection conn;

            int responseCode;

            try {
                URL loginUrl = new URL(url);

                conn = (HttpURLConnection) loginUrl.openConnection();

                conn.setRequestMethod(method);
                conn.setConnectTimeout(Constants.API_TIMEOUT_MILIS);
                //conn.setReadTimeout();
                conn.setDoInput(true);

                if (method.equals("POST") && requestJson != null) {
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                    Log.d(AsyncTAG, "json:" + requestJson.toString());

                    bw.write(requestJson.toString());
                    bw.flush();

                    bw.close();
                }

                responseCode = conn.getResponseCode();

            } catch (SocketTimeoutException e) {
                Log.e(AsyncTAG, "Timeout!");
                return null;
            } catch (UnsupportedEncodingException e) {  //TODO: EMAGHERD EXCEPTIENS
                e.printStackTrace();
                return null;
            } catch (ProtocolException e) {
                e.printStackTrace();
                return null;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line, response = "";

                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    while ((line = br.readLine()) != null) {
                        Log.d(AsyncTAG, "Response line: " + line);
                        response += line;
                    }

                    br.close();

                } catch (IOException e) {
                    Log.e(AsyncTAG, "Error mentre es llegia la resposta.");
                    e.printStackTrace();
                    return null;
                }


                JSONObject json;
                try {
                    json = new JSONObject(response);

                    return json;

                } catch (JSONException e) {
                    Log.e(AsyncTAG, "JSON malformat o Resposta JSON no es la esperada!");
                    return null;
                }

            } else {
                Log.e(AsyncTAG, "Response code is not OK! (" + responseCode + ")");
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            Log.d(AsyncTAG, ".onPostExecute() cridat");

            callbackClass.onAPICallback(json);
        }
    }

    public void setRequestJson(JSONObject json) {
        this.requestJson = json;
    }

    public void setRequestJson(String jsonString) throws JSONException {
        requestJson = new JSONObject(jsonString);
    }

    public JSONObject getRequestJson() {
        return requestJson;
    }

    //Metode per seleccionar a qui crida quan acaba la peticio
    public void setOnAPICallbackListener(APICallbackListener cbClass) {
        callbackClass = cbClass;
    }

    public void doAPICall() {
        Log.d(TAG, ".doAPICall() cridat");

        APICallAsyncTask asyncTask = new APICallAsyncTask();
        asyncTask.execute();
    }
}
