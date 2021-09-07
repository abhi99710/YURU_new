package com.app.yuru.ui.testResult

import android.view.LayoutInflater
import com.app.yuru.coreandroid.base.BaseActivityBinding
import com.app.yuru.databinding.ActivityTestResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestResultActivity : BaseActivityBinding<ActivityTestResultBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityTestResultBinding
        get() = ActivityTestResultBinding::inflate

    override fun setupView(binding: ActivityTestResultBinding) {

    }
}