package com.app.yuru.ui.login

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.app.yuru.R
import com.app.yuru.coreandroid.base.BaseFragmentBinding
import com.app.yuru.databinding.FragmentLoginBinding
import com.app.yuru.ui.getStarted.GetStartedActivity
import com.app.yuru.ui.test.TestActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragmentBinding<FragmentLoginBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun setupView(binding: FragmentLoginBinding) {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_signUp)


        }
        binding.btnProceed.setOnClickListener{

            Toast.makeText(context, "Login", Toast.LENGTH_SHORT).show()
            startActivity(Intent(context, GetStartedActivity::class.java))

        }
    }
}