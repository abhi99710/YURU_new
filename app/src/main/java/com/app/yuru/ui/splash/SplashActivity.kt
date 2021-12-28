package com.app.yuru.ui.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.app.yuru.R
import com.app.yuru.ui.coupons.DiscountCode
import com.app.yuru.ui.discounts.CalenderV
import com.app.yuru.ui.discounts.Survey
import com.app.yuru.ui.lowvshigh.LowvsHigh
import com.app.yuru.ui.transition.TransitionActivity
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())

    private val runnable = Runnable {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            startActivity(Intent(this, PermissionActivity::class.java))
        } else {
//        startActivity(Intent(this, Splash2::class.java))
//            startActivity(Intent(this, TransitionActivity::class.java))
//        startActivity(Intent(this, Survey::class.java))
        startActivity(Intent(this, CalenderV::class.java))
        }
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(2))
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }
}