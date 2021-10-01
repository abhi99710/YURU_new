package com.app.yuru.ui.test

import com.app.yuru.domain.entity.Json4Kotlin_Base
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.yuru.coreandroid.exception.Failure
import com.app.yuru.domain.usecase.QuestionUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class TestViewModel @Inject constructor(val questionUseCase: QuestionUseCase) : ViewModel() {
    val pageLiveData = MutableLiveData<String>()
    fun uiState(): LiveData<QuestionState> = uiState
    protected val uiState: MutableLiveData<QuestionState> = MutableLiveData()

    sealed class QuestionState {
        object Loading : TestViewModel.QuestionState()
        data class Success(val questionResponse: Json4Kotlin_Base) : TestViewModel.QuestionState()
        data class Error(val message: String) : TestViewModel.QuestionState()
    }

    fun getQuestions(category_id : String) {
        viewModelScope.launch {
            uiState.postValue(TestViewModel.QuestionState.Loading)
            val newsList =
                questionUseCase.run(QuestionUseCase.QuestionSParams(category_id))
            newsList.fold({ failure ->
                when (failure) {
                    is Failure.ServerError -> {
                        uiState.postValue(TestViewModel.QuestionState.Error("Server Error"))
                    }
                    is Failure.NOKError -> {
                        uiState.postValue(TestViewModel.QuestionState.Error(failure.message ?: ""))
                    }
                    else -> uiState.postValue(TestViewModel.QuestionState.Error("Unknown Error"))
                }

            }, { result ->
                uiState.postValue(TestViewModel.QuestionState.Success(result))
            })
        }
    }

}