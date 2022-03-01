package com.app.yuru.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.yuru.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VerifyOtp extends AppCompatActivity {

    private AppCompatButton btn_verify;
    private AppCompatEditText email_verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        btn_verify = findViewById(R.id.btn_verify);
        email_verify = findViewById(R.id.email_verify);

        String otp = getIntent().getStringExtra("otp");
        Toast.makeText(this, ""+otp, Toast.LENGTH_SHORT).show();

        btn_verify.setOnClickListener(v->{
            if(email_verify.getText().toString().isEmpty()){
                Toast.makeText(this, "Please enter OTP", Toast.LENGTH_SHORT).show();
            }else if(otp.equalsIgnoreCase(email_verify.getText().toString())){
                startActivity(new Intent(VerifyOtp.this, ResetPassword.class));
            }else {
                Toast.makeText(this, "OTP doesnot match", Toast.LENGTH_SHORT).show();
            }
        });

    }

}