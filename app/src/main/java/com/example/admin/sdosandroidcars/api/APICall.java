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

    //Per defecte, APICall executara una peticio "GET"
    public APICall(String url) {
        this("GET", url);
    }

    //AsyncTask que retorna el json(dades del filter) del servidor
    private class APICallAsyncTask extends AsyncTask<Void, Void, JSONObject> {

        private final String AsyncTAG = "AsyncTask/" + TAG;

        @Override
        protected JSONObject doInBackground(Void... voids) {
            Log.d(AsyncTAG, ".doInBackground() cridat");

            Log.d(TAG, "method:" + method + " url:" + url);

            //Conexio instanciada
            HttpURLConnection conn;

            int responseCode;

            try {
                //url que se li pasa a la clase APICall al instanciarla (/getInfo)
                URL loginUrl = new URL(url);

                //obrim conexio amb el servidor
                conn = (HttpURLConnection) loginUrl.openConnection();

                //metode POST o GET
                conn.setRequestMethod(method);
                //Timeout de la conexió per a que mori al no contestar
                conn.setConnectTimeout(Constants.API_TIMEOUT_MILIS);
                //conn.setReadTimeout();
                conn.setDoInput(true);


                if (method.equals("POST") && requestJson != null) {
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);

                    //os conte la resposta del servidor (json)
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                    Log.d(AsyncTAG, "json:" + requestJson.toString());

                    //escrivim la resposta al requestJson
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

            //Si la peticio ha anat be
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line, response = "";

                try {
                    //creem un reader de la conexion
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    //llegim conexio i afegim la resposta a response
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

                //copiem la resposta (string) al json, en format json i el retornem (al onPostExecute)
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
        //Una vegada retornat el json, retornem la resposta a qui ha cridat APICall (Info)
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

    //Metode que executa l'asyncTask de la peticio de les dades al servidor
    public void doAPICall() {
        Log.d(TAG, ".doAPICall() cridat");

        APICallAsyncTask asyncTask = new APICallAsyncTask();
        asyncTask.execute();
    }
}
