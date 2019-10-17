package com.example.puray.rest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

public class RegistrationActivity extends AppCompatActivity {

    Button btnReg;
    EditText etEmail;
    EditText etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btnReg = findViewById(R.id.btnReg);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);

        registerUser("mbuguapurity@gmail.com", "98089909");


        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
            }
        });
    }

    public  void registerUser(String email, String password){
        ApiInterface apiInterface = ApiClient.createRetrofit().create(ApiInterface.class);

        Call<RegistrationResponse>registrationCall = apiInterface.registerUser(email, password);
        registrationCall.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                RegistrationResponse registrationResponse = response.body();
                SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                editor.putString("AUTH_TOKEN",registrationResponse.getAuthToken());
                editor.putInt("USER_ID",registrationResponse.getUserId());
                editor.apply();

                Log.d("haiyaaa", response.toString());

            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Log.d("failed", t.getMessage());

            }
        });
    }
}
