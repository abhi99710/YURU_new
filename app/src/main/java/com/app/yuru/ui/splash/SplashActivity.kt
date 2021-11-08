package com.app.yuru.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.app.yuru.R
import com.app.yuru.ui.coupons.Journals
import com.app.yuru.ui.login.LoginActivity
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())

    private val runnable = Runnable {
//        startActivity(Intent(this, Splash2::class.java))
//        startActivity(Intent(this, Splash2::class.java))
        startActivity(Intent(this, Journals::class.java))
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