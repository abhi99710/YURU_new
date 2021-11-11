package com.app.yuru.ui.coupons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.app.yuru.R;
import com.app.yuru.ui.discounts.CouponApplied;

public class DiscountCode extends AppCompatActivity {

    private Button buttonSubmitCoupon;
    private EditText enterCoupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_code);

        enterCoupon = findViewById(R.id.enterCoupon);
        buttonSubmitCoupon = findViewById(R.id.buttonSubmitCoupon);
        buttonSubmitCoupon.setOnClickListener(v->{
            startActivity(new Intent(this, CouponApplied.class));
        });

    }
}