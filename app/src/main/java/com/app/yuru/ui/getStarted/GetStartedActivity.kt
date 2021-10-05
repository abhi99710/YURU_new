package com.app.yuru.ui.getStarted

import android.content.Intent
import android.view.LayoutInflater
import com.app.yuru.R
import com.app.yuru.coreandroid.base.BaseActivityBinding
import com.app.yuru.databinding.ActivityGetStartedBinding
import com.app.yuru.ui.test.TestActivity

const val ARG_NAME = "name"

class GetStartedActivity : BaseActivityBinding<ActivityGetStartedBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityGetStartedBinding
        get() = ActivityGetStartedBinding::inflate

    override fun setupView(binding: ActivityGetStartedBinding) {
        val name = intent.getStringExtra(ARG_NAME)
        binding.tvName.text = getString(R.string.hi_s, name)
        binding.btnGetStarted.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
            finish()
        }
    }
}