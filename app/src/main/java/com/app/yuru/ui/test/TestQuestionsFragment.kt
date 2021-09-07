package com.app.yuru.ui.test

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.yuru.coreandroid.base.BaseFragmentBinding
import com.app.yuru.databinding.FragmentTestQuestionsBinding
import javax.inject.Inject

abstract class TestQuestionsFragment : BaseFragmentBinding<FragmentTestQuestionsBinding>() {
    @Inject
    lateinit var viewModel: TestViewModel
    lateinit var adapter: TestQuestionsAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTestQuestionsBinding
        get() = FragmentTestQuestionsBinding::inflate

    override fun setupView(binding: FragmentTestQuestionsBinding) {
        binding.tvTitle.text = getPageTitle()
        if (!::adapter.isInitialized) {
            adapter = TestQuestionsAdapter(baseActivity, getTestQuestionsListener())
        }
        binding.rvQuestions.adapter = adapter
    }

    abstract fun getPageTitle(): String
    abstract fun getTestQuestionsListener(): TestQuestionsListener
}