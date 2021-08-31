package com.app.yuru.ui.homepage

import android.view.LayoutInflater
import com.app.yuru.databinding.ActivityHomeBinding
import com.github.ajalt.timberkt.d
import com.app.yuru.coreandroid.base.BaseActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageActivity : BaseActivityBinding<ActivityHomeBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    override fun setupView(binding: ActivityHomeBinding) {
        d { "setup view" }
    }

}