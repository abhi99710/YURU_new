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

public class ResetPassword extends AppCompatActivity {

    private AppCompatButton btn_reset;
    private AppCompatEditText password_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        btn_reset = findViewById(R.id.btn_reset);
        password_reset = findViewById(R.id.password_reset);

        btn_reset.setOnClickListener(v->{
            if(password_reset.getText().toString().isEmpty()){
                Toast.makeText(this, "Please enter new Password", Toast.LENGTH_SHORT).show();
            }else {

                apiResetPassword();

            }
        });

    }

    private void apiResetPassword() {

        String url = "http://app.whyuru.com/api/web/resetPassword";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                int code = jsonObject.getInt("code");
                JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                String mes = jsonObject1.getString("message");
                Toast.makeText(this, ""+mes, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, LoginActivity.class);
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
                map.put("password", password_reset.getText().toString());
                map.put("mail", ForgotPassword.email);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}