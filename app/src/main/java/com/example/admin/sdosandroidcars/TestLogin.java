package com.example.admin.sdosandroidcars;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.sdosandroidcars.login.Login;
import com.example.admin.sdosandroidcars.login.LoginResultListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class TestLogin extends PermissionManager implements View.OnClickListener {

    private static final String TAG = "LoginTest";
    EditText editTextUsername;
    EditText editTextPassword;
    
    Button buttonLogin;

    private boolean permissions = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_login);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(this);

        addPermission(Manifest.permission.INTERNET);

        requestPermissions();
    }

    @Override
    protected void onPermissionRequestDone(boolean successAll, ArrayList<String> grantedPermissions) {
        Log.d(TAG, ".onPermissionRequestDone() cridat");

        Log.d(TAG, "successAll:" + successAll);

        if (successAll) permissions = true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        
        switch (id) {
            case R.id.buttonLogin:
                Log.d(TAG, "buttonLogin clickat");

                if (permissions) {
                    String username = editTextUsername.getText().toString();
                    String password = editTextPassword.getText().toString();

                    Log.d(TAG, "user:'" + username + "'");
                    Log.d(TAG, "password:'" + password + "'");

                    Login login = new Login(username, password);

                    final TestLogin self = this;
                    login.setOnLoginResultListener(new LoginResultListener() {
                        @Override
                        public void onLoginResult(JSONObject json) {
                            Toast.makeText(self, json.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    login.doLogin();

                } else {
                    Log.d(TAG, "No tenim els permisos necessaris");

                    Toast toast = Toast.makeText(this, "No hi ha permisos!", Toast.LENGTH_SHORT);
                    toast.show();
                }
        }
    }
}
