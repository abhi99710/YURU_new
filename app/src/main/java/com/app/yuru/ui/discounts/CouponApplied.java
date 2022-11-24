package com.app.yuru.ui.discounts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ImageView;

import com.app.yuru.R;
import com.app.yuru.ui.coupons.DiscountCode;
import com.app.yuru.ui.login.AnimLogin;
import com.app.yuru.ui.splash.PermissionActivity;
import com.app.yuru.ui.transition.TransitionActivity;

public class CouponApplied extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_applied);

        ImageView monthlyGo = findViewById(R.id.monthlyGo);
        ImageView yearlyGo = findViewById(R.id.yearlyGo);
        ImageView backSubscription = findViewById(R.id.backSubscription);

        backSubscription.setOnClickListener(v-> {
            startActivity(new Intent(this, DiscountCode.class));
        });

        monthlyGo.setOnClickListener(v-> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                startActivity(new Intent(this, PermissionActivity.class));
            }else{
                startActivity(new Intent(this, TransitionActivity.class));
            }
//            startActivity(new Intent(this, MoreInformation.class));
        });

        yearlyGo.setOnClickListener(v->{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                startActivity(new Intent(this, PermissionActivity.class));
            }else{
                startActivity(new Intent(this, TransitionActivity.class));
            }
        } );
//        startActivity(new Intent(this, MoreInformation.class)));
    }
}