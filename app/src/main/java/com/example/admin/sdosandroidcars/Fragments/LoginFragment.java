package com.example.admin.sdosandroidcars.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.sdosandroidcars.Drawer;
import com.example.admin.sdosandroidcars.R;
import com.example.admin.sdosandroidcars.api.login.Login;
import com.example.admin.sdosandroidcars.api.login.LoginResultListener;
import com.example.admin.sdosandroidcars.api.login.SessionManager;

import org.json.JSONObject;

public class LoginFragment extends BaseFragment implements View.OnClickListener, LoginResultListener {

    String TAG = "LogIn";
    Button okBtn;
    EditText username;
    EditText password;
    SessionManager sessionManager;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.frag_login, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Login");

        sessionManager = ((Drawer)getActivity()).sessionManager;

        okBtn = (Button) getView().findViewById(R.id.buttonLoginOk);
        okBtn.setOnClickListener(this);

        username = (EditText) getView().findViewById(R.id.loginName);
        password = (EditText) getView().findViewById(R.id.loginPwd);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        final String user =  username.getText().toString();
        final String pwd = password.getText().toString();

        if (id == (R.id.buttonLoginOk)) {
                if (user.isEmpty() || pwd.isEmpty()) {
                    Toast.makeText(getContext(), "Empty username or password!", Toast.LENGTH_SHORT).show();
                } else {
                    Login login = new Login(user, pwd);
                    login.setOnLoginResultListener(new LoginResultListener() {
                        @Override
                        public void onLoginResult(JSONObject json) {
                            if (json.length()!=0) {
                                if (json.optString("err").isEmpty()) {
                                    Toast.makeText(getContext(), "Login Correct", Toast.LENGTH_SHORT).show();
                                    SessionManager.createLoginSession(user,pwd);
                                    ((Drawer)getActivity()).show();
                                }
                                else
                                    Toast.makeText(getContext(), json.optString("err"), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getContext(), "Something is wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    try {
                        login.doLogin();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
    }

    @Override
    public void onLoginResult(JSONObject json) {


    }
}
