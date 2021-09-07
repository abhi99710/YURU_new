package com.app.yuru.ui.test

import androidx.navigation.fragment.findNavController
import com.app.yuru.R
import com.app.yuru.databinding.FragmentTestQuestionsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CTestQuestionsFragment : TestQuestionsFragment() {
    override fun setupView(binding: FragmentTestQuestionsBinding) {
        super.setupView(binding)
        viewModel.pageLiveData.postValue("C")
    }

    override fun getPageTitle(): String {
        return "Let’s see where do you stand;"
    }

    override fun getTestQuestionsListener(): TestQuestionsListener {
        return object : TestQuestionsListener {
            override fun onNextClicked() {
                findNavController().navigate(R.id.actionCE)
            }
        }
    }
}