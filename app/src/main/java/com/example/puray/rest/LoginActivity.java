package com.example.puray.rest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.puray.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPass;
    Button btnlogin;
    Button btnRegistration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        btnlogin = findViewById(R.id.btnlogin);
        btnRegistration = findViewById(R.id.btnRegistration);

            btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), RegistrationActivity.class));
            }
        });

    }

    public void loginUser(String email, String password){
        ApiInterface apiInterface = ApiClient.createRetrofit().create(ApiInterface.class);
        Call<LoginResponse> loginCall = apiInterface.loginUser(email,password);
        loginCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.code() == 200){
                    LoginResponse LoginResponse = response.body();
                    SharedPreferences mySharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor editor = mySharedPrefs.edit();
                    editor.putString("AUTH_TOKEN",LoginResponse.getAuthToken());
                    editor.putInt("USER_ID",LoginResponse.getUserId());
                    editor.apply();
                    Log.d("success",response.toString());
                }
                else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
                    alertDialog.setTitle("credentials");
                    alertDialog.setMessage("Enter the correct email and password to continue");
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("faiure",t.getMessage());
            }
        });
    }
}
