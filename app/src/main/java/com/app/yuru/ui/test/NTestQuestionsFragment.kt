package com.app.yuru.ui.test

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.yuru.databinding.FragmentTestQuestionsBinding
import com.app.yuru.ui.testResult.TestResultActivity
import com.app.yuru.utility.concatArray
import com.app.yuru.utility.showToast
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray

@AndroidEntryPoint
class NTestQuestionsFragment : TestQuestionsFragment() {
    override fun setupView(binding: FragmentTestQuestionsBinding) {
        super.setupView(binding)
        viewModel.pageLiveData.postValue("N")

        viewModel.getQuestions("5")

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
                activityViewModel.questions.concatArray(jsonArray)
                viewModelSubmit.submitResponse(
                    "1",
                    activityViewModel.questions.toString()
                ) // userid needed

                viewModelSubmit.uiState().observe(this@NTestQuestionsFragment, {

                    when (it) {
                        is SubmitResponseModel.SubmitResponseSealed.Loading -> {
                            baseActivity.showToast("Loading...")
                        }
                        is SubmitResponseModel.SubmitResponseSealed.Error -> {
//                            baseActivity.showToast(it.message)
                            startActivity(Intent(baseActivity, TestResultActivity::class.java))

                        }
                        is SubmitResponseModel.SubmitResponseSealed.Success -> {
                            baseActivity.showToast(it.questionResponse.result.message)
                            startActivity(Intent(baseActivity, TestResultActivity::class.java))
                        }
                    }
                })
            }
        }
    }
}