package com.app.yuru.ui.test

import android.view.LayoutInflater
import android.view.View
import com.app.yuru.coreandroid.base.BaseActivityBinding
import com.app.yuru.databinding.ActivityTestBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TestActivity : BaseActivityBinding<ActivityTestBinding>() {
    @Inject
    lateinit var viewModel: TestActivityViewModel

    override val bindingInflater: (LayoutInflater) -> ActivityTestBinding
        get() = ActivityTestBinding::inflate

    override fun setupView(binding: ActivityTestBinding) {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        resetStep()
        viewModel.pageLiveData.observe(this, {
            resetStep()
            when (it.uppercase()) {
                "O" -> {
                    binding.stepO.visibility = View.VISIBLE
                }
                "C" -> {
                    binding.stepO.visibility = View.VISIBLE
                    binding.stepC.visibility = View.VISIBLE
                }
                "E" -> {
                    binding.stepO.visibility = View.VISIBLE
                    binding.stepC.visibility = View.VISIBLE
                    binding.stepE.visibility = View.VISIBLE
                }
                "A" -> {
                    binding.stepO.visibility = View.VISIBLE
                    binding.stepC.visibility = View.VISIBLE
                    binding.stepE.visibility = View.VISIBLE
                    binding.stepA.visibility = View.VISIBLE
                }
                "N" -> {
                    binding.stepO.visibility = View.VISIBLE
                    binding.stepC.visibility = View.VISIBLE
                    binding.stepE.visibility = View.VISIBLE
                    binding.stepA.visibility = View.VISIBLE
                    binding.stepN.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun resetStep() {
        binding.stepO.visibility = View.VISIBLE
        binding.stepC.visibility = View.INVISIBLE
        binding.stepE.visibility = View.INVISIBLE
        binding.stepA.visibility = View.INVISIBLE
        binding.stepN.visibility = View.INVISIBLE
    }
}