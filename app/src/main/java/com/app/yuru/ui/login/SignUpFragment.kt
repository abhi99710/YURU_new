package com.app.yuru.ui.login

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.yuru.coreandroid.base.BaseFragmentBinding
import com.app.yuru.databinding.FragmentSignupBinding
import com.app.yuru.ui.getStarted.GetStartedActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragmentBinding<FragmentSignupBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSignupBinding
        get() = FragmentSignupBinding::inflate

    override fun setupView(binding: FragmentSignupBinding) {
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(baseActivity, GetStartedActivity::class.java))
            baseActivity.finish()
        }
    }
}