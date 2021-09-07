package com.app.yuru.ui.testResult

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.yuru.coreandroid.base.BaseFragmentBinding
import com.app.yuru.databinding.FragmentTestQuestionsBinding
import com.app.yuru.databinding.FragmentTestResultsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestResultFragment : BaseFragmentBinding<FragmentTestResultsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTestResultsBinding
        get() = FragmentTestResultsBinding::inflate

    override fun setupView(binding: FragmentTestResultsBinding) {

    }
}