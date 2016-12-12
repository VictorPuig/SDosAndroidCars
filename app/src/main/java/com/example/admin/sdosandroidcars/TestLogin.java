package com.example.admin.sdosandroidcars;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.sdosandroidcars.api.cars.Car;
import com.example.admin.sdosandroidcars.api.cars.Cars;
import com.example.admin.sdosandroidcars.api.cars.FilteredCarsResultListener;
import com.example.admin.sdosandroidcars.api.info.Filter;
import com.example.admin.sdosandroidcars.api.info.Info;
import com.example.admin.sdosandroidcars.api.info.InfoResultListener;
import com.example.admin.sdosandroidcars.api.login.Login;
import com.example.admin.sdosandroidcars.api.login.LoginResultListener;
import com.example.admin.sdosandroidcars.api.login.Signup;
import com.example.admin.sdosandroidcars.api.login.SignupResultListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class TestLogin extends PermissionManager implements View.OnClickListener {

    private static final String TAG = "LoginTest";
    EditText editTextUsername;
    EditText editTextPassword;
    
    Button buttonLogin;
    Button buttonSignup;
    Button buttonGetInfo;
    Button buttonGetCars;

    private boolean permissions = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_login);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        buttonGetInfo = (Button) findViewById(R.id.buttonGetInfo);
        buttonGetCars = (Button) findViewById(R.id.buttonGetCars);

        buttonLogin.setOnClickListener(this);
        buttonSignup.setOnClickListener(this);
        buttonGetInfo.setOnClickListener(this);
        buttonGetCars.setOnClickListener(this);

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

        if (!permissions) {
            Log.d(TAG, "No tenim els permisos necessaris");

            Toast.makeText(this, "No hi ha permisos!", Toast.LENGTH_SHORT).show();

            return;
        }

        if (id == R.id.buttonLogin) {
            Log.d(TAG, "buttonLogin clickat");

            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            Log.d(TAG, "user:'" + username + "'");
            Log.d(TAG, "password:'" + password + "'");

            Login login = new Login(username, password);

            final TestLogin self = this;
            login.setOnLoginResultListener(new LoginResultListener() {
                @Override
                public void onLoginResult(JSONObject json) {
                    String show = (json == null) ? "Error" : json.toString();  //TODO: OMG
                    Toast.makeText(self, show, Toast.LENGTH_SHORT).show();
                }
            });

            try {
                login.doLogin();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (id == R.id.buttonSignup) {
            Log.d(TAG, "buttonSignup clickat");

            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            Log.d(TAG, "user:'" + username + "'");
            Log.d(TAG, "password:'" + password + "'");

            Signup signup = new Signup(username, password);

            final TestLogin self = this;
            signup.setOnSignupResultListener(new SignupResultListener() {
                @Override
                public void onSignupResult(JSONObject json) {
                    String show = (json == null) ? "Error" : json.toString();  //TODO: OMG
                    Toast.makeText(self, show, Toast.LENGTH_SHORT).show();
                }
            });

            try {
                signup.doSignup();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.buttonGetInfo) {
            Log.d(TAG, "buttonGetInfo clickat");

            final TestLogin self = this;
            Info.doGetInfo(new InfoResultListener() {
                @Override
                public void onInfoResult(Filter filter) {
                    String show = (filter == null) ? "Error" : filter.getJSONObject().toString();  //TODO: OMG
                    Toast.makeText(self, show, Toast.LENGTH_SHORT).show();
                }
            });
        } else if (id == R.id.buttonGetCars) {
            Log.d(TAG, "buttonGetCars clickat");

            final TestLogin self = this;
            try {
                Cars.doGetCars(new JSONObject("{maker:[],color:[]}"), new FilteredCarsResultListener() {
                    @Override
                    public void onCarsResult(ArrayList<Car> cars) {
                        String show = cars.toString();
                        Toast.makeText(self, show, Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
