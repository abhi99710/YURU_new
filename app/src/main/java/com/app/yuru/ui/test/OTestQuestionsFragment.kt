package com.app.yuru.ui.test

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.yuru.R
import com.app.yuru.databinding.FragmentTestQuestionsBinding
import com.app.yuru.databinding.ItemTestQuestionsBinding
import com.app.yuru.utility.showToast
import com.github.florent37.application.provider.application
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.NonDisposableHandle.parent
import org.json.JSONArray

@AndroidEntryPoint
class OTestQuestionsFragment : TestQuestionsFragment() {

    override fun setupView(binding: FragmentTestQuestionsBinding) {
        super.setupView(binding)
        viewModel.pageLiveData.postValue("O")

        viewModel.getQuestions("1")

        viewModel.uiState().observe(this, {

            when(it){
                    is TestViewModel.QuestionState.Loading->{
                       baseActivity.showToast("Loading...")
                    }
                is TestViewModel.QuestionState.Error ->{
                    baseActivity.showToast(it.message)
                }
                is  TestViewModel.QuestionState.Success ->{
                    val testQuestionsAdapter : TestQuestionsAdapter = TestQuestionsAdapter(requireActivity(),
                        getTestQuestionsListener(), it.questionResponse,"o")
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
                findNavController().navigate(R.id.actionOC)
            }
        }
    }
}