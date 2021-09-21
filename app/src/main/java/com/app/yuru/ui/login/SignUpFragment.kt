package com.app.yuru.ui.login

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.yuru.coreandroid.base.BaseFragmentBinding
import com.app.yuru.databinding.FragmentSignupBinding
import com.app.yuru.ui.getStarted.GetStartedActivity
import com.app.yuru.utility.showToast
import com.app.yuru.utility.trimString
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : BaseFragmentBinding<FragmentSignupBinding>() {
    @Inject
    lateinit var signUpViewModel: SignUpViewModel

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSignupBinding
        get() = FragmentSignupBinding::inflate

    override fun setupView(binding: FragmentSignupBinding) {
        binding.btnSignUp.setOnClickListener {
            val fullName = binding.edtName.trimString()
            val email = binding.edtEmail.trimString()
            val password = binding.edtPassword.trimString()
            when {
                fullName.isBlank() -> {
                    baseActivity.showToast("Please enter name")
                }
                email.isBlank() -> {
                    baseActivity.showToast("Please enter email")
                }
                password.isBlank() -> {
                    baseActivity.showToast("Please enter password")
                }
                else -> {
                    signUpViewModel.register(fullName, email, password)
                }
            }
        }
        signUpViewModel.uiState().observe(this, {
            binding.progressBar.visibility = View.GONE
            binding.btnSignUp.isEnabled = true
            when (it) {
                is SignUpViewModel.SignUpState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignUp.isEnabled = false
                }
                is SignUpViewModel.SignUpState.Error -> {
                    baseActivity.showToast(it.message)
                }
                is SignUpViewModel.SignUpState.Success -> {
                    startActivity(Intent(baseActivity, GetStartedActivity::class.java))
                    baseActivity.finish()
                }
            }
        })
    }
}