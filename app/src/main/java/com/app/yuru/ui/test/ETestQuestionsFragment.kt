package com.app.yuru.ui.test

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.yuru.R
import com.app.yuru.databinding.FragmentTestQuestionsBinding
import com.app.yuru.utility.showToast
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray

@AndroidEntryPoint
class ETestQuestionsFragment : TestQuestionsFragment() {
    override fun setupView(binding: FragmentTestQuestionsBinding) {
        super.setupView(binding)
        stepViewModel.pageLiveData.postValue("E")

        viewModel.getQuestions("3")

        viewModel.uiState().observe(this, {

            when (it) {
                is TestViewModel.QuestionState.Loading -> {
                    baseActivity.showToast("Loading...")
                }
                is TestViewModel.QuestionState.Error -> {
                    baseActivity.showToast(it.message)
                }
                is TestViewModel.QuestionState.Success -> {
                    val testQuestionsAdapter = TestQuestionsAdapter(
                        requireActivity(),
                        getTestQuestionsListener(),
                        it.questionResponse
                    )
                    binding.rvQuestions.layoutManager = LinearLayoutManager(context)
                    binding.rvQuestions.setHasFixedSize(true)
                    binding.rvQuestions.adapter = testQuestionsAdapter
                    submitPageTitle(it.questionResponse.result.data.title)
                }
            }


        })
    }

    override fun getTestQuestionsListener(): TestQuestionsListener {
        return object : TestQuestionsListener {
            override fun onNextClicked(jsonArray: JSONArray) {
                findNavController().navigate(R.id.actionEA)
            }
        }
    }
}