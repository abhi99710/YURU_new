package com.app.yuru.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

public class ForgotPassword extends AppCompatActivity {

    private AppCompatButton btn_forgot;
    private AppCompatEditText email_forgot;
    public static String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        btn_forgot = findViewById(R.id.btn_forgot);
        email_forgot = findViewById(R.id.email_forgot);

        btn_forgot.setOnClickListener(v->{
            if(email_forgot.getText().toString().isEmpty()){
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            }else {
                email = email_forgot.getText().toString();
                apiForgotPassword();
            }
        });

    }

    private void apiForgotPassword() {

        String url = "http://app.whyuru.com/api/web/checkMailAndSendOTP";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                int code = jsonObject.getInt("code");
                JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                String mes = jsonObject1.getString("message");
                Toast.makeText(this, ""+mes, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, VerifyOtp.class);
                intent.putExtra("otp", ""+jsonObject1.getInt("otp"));
                startActivity(intent);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("mail",email_forgot.getText().toString());
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}