package com.app.yuru.ui.testResult

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.yuru.coreandroid.base.BaseFragmentBinding
import com.app.yuru.databinding.FragmentTestResultsClosureLookBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestResultClosureLookFragment : BaseFragmentBinding<FragmentTestResultsClosureLookBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTestResultsClosureLookBinding
        get() = FragmentTestResultsClosureLookBinding::inflate

    override fun setupView(binding: FragmentTestResultsClosureLookBinding) {

    }
}