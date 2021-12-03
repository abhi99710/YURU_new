package com.app.yuru.ui.coupons;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.yuru.R;
import com.app.yuru.ui.discounts.CouponApplied;
import com.app.yuru.utility.apivolley.APIVolley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DiscountCode extends AppCompatActivity {

    private Button buttonSubmitCoupon;
    private EditText enterCoupon;
    private String discount_code;
    private TextView resendCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_code);

        resendCode = findViewById(R.id.resendCode);
//        resendCode.setEnabled(false);
        new Handler().postDelayed(() -> {
//            resendCode.setEnabled(true);
        },10000);

//        resendCode.setOnClickListener(v->{
//            apiGetCoupon();  // calling get coupon code api
//        });

        apiGetCoupon();  // calling get coupon code api

        enterCoupon = findViewById(R.id.enterCoupon);
        buttonSubmitCoupon = findViewById(R.id.buttonSubmitCoupon);
        buttonSubmitCoupon.setOnClickListener(v->{

            if(enterCoupon.getText().toString().equalsIgnoreCase(discount_code)){
                startActivity(new Intent(this, CouponApplied.class));
            }
            else {
                Toast.makeText(this, "Entered discount Coupon is Wrong.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*------ getting coupon code and save it locally ------*/
    private void apiGetCoupon() {

        String url = APIVolley.Companion.getGenerateCode()+"?user_id="+"1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                 discount_code = jsonObject1.getString("discount_code");
                Toast.makeText(this, ""+discount_code, Toast.LENGTH_SHORT).show();
                    if(!discount_code.equalsIgnoreCase("0")) {
                        apiSenderMail("1");
                    }
                    else {
                        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                    }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "server waiting...", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /*------ Send email to users email address ------*/
    private void apiSenderMail(String s){
        String url = APIVolley.Companion.getSendemail();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                boolean valid = jsonObject.getBoolean("valid");
                if(valid)
                Toast.makeText(this, "Code sent to your email ", Toast.LENGTH_SHORT).show();

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "server waiting...", Toast.LENGTH_SHORT).show()){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> ma1 = new HashMap<>();
                ma1.put("user_id", "1");
                return ma1;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}