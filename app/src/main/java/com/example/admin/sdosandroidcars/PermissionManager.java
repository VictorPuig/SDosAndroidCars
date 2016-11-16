package com.example.admin.sdosandroidcars;


import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class PermissionManager extends AppCompatActivity {

    private static final String TAG = "Permission Manager";

    private ArrayList<String> requestedPermissions = new ArrayList<>();

    protected void addPermission(String permission) {
        requestedPermissions.add(permission);
    }

    public void requestPermissions() {
        Log.d(TAG, ".requestPermissions() cridat");

        Log.d(TAG, "Permission:" + requestedPermissions.get(0));
        if (ContextCompat.checkSelfPermission(this, requestedPermissions.get(0)) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "No tenim aquest permis");

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, requestedPermissions.get(0))) {
                Log.d(TAG, "Demanant permisos");

                ActivityCompat.requestPermissions(this, new String[]{requestedPermissions.get(0)}, 1);
            } else {
                Log.d(TAG, "L'usuari ha denegat el permis, i no vol que el demanem mes");
            }
        } else {
            Log.d(TAG, "Ja tenim aquest permis");

            onPermissionRequestDone(true, new ArrayList<String>());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult cridat");

        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < permissions.length; i++) {
            Log.d(TAG, "Permission:" + permissions[i] + "/Result:" + grantResults[i]);
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                result.add(permissions[i]);
            }
        }

        boolean successAll = permissions.length == result.size();

        onPermissionRequestDone(successAll, result);
    }

    protected void onPermissionRequestDone(boolean successAll, ArrayList<String> grantedPermissions) {
        Log.w(TAG, "Funcio onPermissionRequestDone() per defecte cridada");
        Log.w(TAG, "Fes un override d'aquesta funcio!");
    }
}
