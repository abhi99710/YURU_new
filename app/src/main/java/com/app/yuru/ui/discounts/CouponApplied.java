package com.app.yuru.ui.discounts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.app.yuru.R;
import com.app.yuru.ui.coupons.DiscountCode;

public class CouponApplied extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_applied);

        ImageView monthlyGo = findViewById(R.id.monthlyGo);
        ImageView yearlyGo = findViewById(R.id.yearlyGo);
        ImageView backSubscription = findViewById(R.id.backSubscription);

        backSubscription.setOnClickListener(v-> startActivity(new Intent(this, DiscountCode.class)));

        monthlyGo.setOnClickListener(v-> startActivity(new Intent(this, MoreInformation.class)));

        yearlyGo.setOnClickListener(v-> startActivity(new Intent(this, MoreInformation.class)));
    }
}