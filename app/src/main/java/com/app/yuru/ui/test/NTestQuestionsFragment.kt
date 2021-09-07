package com.app.yuru.ui.test

import android.content.Intent
import com.app.yuru.databinding.FragmentTestQuestionsBinding
import com.app.yuru.ui.testResult.TestResultActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NTestQuestionsFragment : TestQuestionsFragment() {
    override fun setupView(binding: FragmentTestQuestionsBinding) {
        super.setupView(binding)
        viewModel.pageLiveData.postValue("N")
    }

    override fun getPageTitle(): String {
        return "Let’s see where do you stand;"
    }

    override fun getTestQuestionsListener(): TestQuestionsListener {
        return object : TestQuestionsListener {
            override fun onNextClicked() {
                startActivity(Intent(baseActivity, TestResultActivity::class.java))
            }
        }
    }
}