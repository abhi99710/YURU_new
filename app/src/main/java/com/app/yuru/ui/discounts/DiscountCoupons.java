package com.app.yuru.ui.discounts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.yuru.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DiscountCoupons extends AppCompatActivity {

    Button btn_coupon_apply;
    TextInputEditText enter_Coupon_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_coupons);

        _init();

        btn_coupon_apply.setOnClickListener(v->{

            if(enter_Coupon_code.getText().toString().isEmpty()){
                Toast.makeText(this, "Please enter coupon code", Toast.LENGTH_SHORT).show();
            }else {
                apiForCoupon();
            }

        });

    }

    private void apiForCoupon() {

        String url = "";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);


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
                map.put("", "");
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void _init() {

        btn_coupon_apply = findViewById(R.id.btn_coupon_apply);
        enter_Coupon_code = findViewById(R.id.enter_Coupon_code);


    }
}