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
import com.example.admin.sdosandroidcars.api.login.SessionManager;
import com.example.admin.sdosandroidcars.api.login.Signup;
import com.example.admin.sdosandroidcars.api.login.SignupResultListener;

import org.json.JSONObject;

public class SignupFragment extends BaseFragment implements View.OnClickListener, SignupResultListener {

    String TAG = "SignUp";
    Button okBtn;
    EditText username;
    EditText password;
    SessionManager sessionManager;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.frag_signup, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("SignUp");

        sessionManager = ((Drawer)getActivity()).sessionManager;

        okBtn = (Button) getView().findViewById(R.id.buttonSignupOk);
        okBtn.setOnClickListener(this);

        username = (EditText) getView().findViewById(R.id.signupName);
        password = (EditText) getView().findViewById(R.id.signupPwd);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        String user =  username.getText().toString();
        String pwd = password.getText().toString();

        if (id == (R.id.buttonSignupOk)) {

                if (user.isEmpty() || pwd.isEmpty()) {
                    Toast.makeText(getContext(), "Empty username or password!", Toast.LENGTH_SHORT).show();
                } else {
                    Signup signup = new Signup(user, pwd);
                    signup.setOnSignupResultListener(this);
                    try {
                        signup.doSignup();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
    }

    @Override
    public void onSignupResult(JSONObject json) {

        if (json.length()!=0) {
            if (json.optJSONObject("errdup") == null)
                Toast.makeText(getContext(), "Signup Correct", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(), "Duplicate username", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Something is wrong", Toast.LENGTH_SHORT).show();
        }



    }
}

