package com.app.yuru.ui.test

import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.yuru.databinding.FragmentTestQuestionsBinding
import com.app.yuru.ui.testResult.TestResultActivity
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

            when(it){
                is TestViewModel.QuestionState.Loading->{
                    baseActivity.showToast("Loading...")
                }
                is TestViewModel.QuestionState.Error ->{
                    baseActivity.showToast(it.message)
                }
                is  TestViewModel.QuestionState.Success ->{
                    val testQuestionsAdapter : TestQuestionsAdapter = TestQuestionsAdapter(requireActivity(), getTestQuestionsListener(), it.questionResponse)
                    binding.rvQuestions.layoutManager = LinearLayoutManager(context)
                    binding.rvQuestions.setHasFixedSize(true)
                    binding.rvQuestions.adapter = testQuestionsAdapter

                }
            }


        })
    }

    override fun getPageTitle(): String {
        return "Let’s see where do you stand;"
    }

    override fun getTestQuestionsListener(): TestQuestionsListener {
        return object : TestQuestionsListener {
            override fun onNextClicked(jsonArray: JSONArray) {

                viewModelSubmit.submitLiveData.postValue("N")
                viewModelSubmit.submitResponse("1", "[]") // userid needed

                viewModelSubmit.uiState().observe(this@NTestQuestionsFragment, {

                    when(it){
                        is SubmitResponseModel.SubmitReaponaeSealed.Loading->{
                            baseActivity.showToast("Loading...")
                        }
                        is  SubmitResponseModel.SubmitReaponaeSealed.Error ->{
                            baseActivity.showToast(it.message)

                        }
                        is   SubmitResponseModel.SubmitReaponaeSealed.Success ->{
                            baseActivity.showToast(it.questionResponse.result.message)

                        }
                    }
                })


                startActivity(Intent(baseActivity, TestResultActivity::class.java))
            }
        }
    }
}