package com.app.yuru.ui.test

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.yuru.coreandroid.base.BaseFragmentBinding
import com.app.yuru.databinding.FragmentTestQuestionsBinding
import javax.inject.Inject

abstract class TestQuestionsFragment : BaseFragmentBinding<FragmentTestQuestionsBinding>() {
    @Inject
    lateinit var viewModel: TestViewModel

    @Inject
    lateinit var activityViewModel: TestActivityViewModel

    lateinit var adapter: TestQuestionsAdapter

    @Inject
    lateinit var viewModelSubmit: SubmitResponseModel

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTestQuestionsBinding
        get() = FragmentTestQuestionsBinding::inflate

    override fun setupView(binding: FragmentTestQuestionsBinding) {

    }

    fun submitPageTitle(title: String) {
        binding.tvTitle.text = title
    }

    abstract fun getTestQuestionsListener(): TestQuestionsListener
}