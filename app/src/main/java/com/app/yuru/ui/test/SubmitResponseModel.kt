package com.app.yuru.ui.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.yuru.coreandroid.exception.Failure
import com.app.yuru.domain.entity.QuestionResponseSubmit
import com.app.yuru.domain.usecase.SubmitResponseUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SubmitResponseModel @Inject constructor(val submit: SubmitResponseUseCase) : ViewModel() {
    val submitLiveData = MutableLiveData<String>()
    fun uiState(): LiveData<SubmitResponseSealed> = uiState
    protected val uiState: MutableLiveData<SubmitResponseSealed> = MutableLiveData()

    sealed class SubmitResponseSealed {
        object Loading : SubmitResponseSealed()
        data class Success(val questionResponse: QuestionResponseSubmit) : SubmitResponseSealed()
        data class Error(val message: String) : SubmitResponseSealed()
    }

    fun submitResponse(user_id: String, answer: String) {
        viewModelScope.launch {
            uiState.postValue(SubmitResponseSealed.Loading)
            val newsList =
                submit.run(SubmitResponseUseCase.SubmitResponse(user_id, answer))
            newsList.fold({ failure ->
                when (failure) {
                    is Failure.ServerError -> {
                        uiState.postValue(SubmitResponseSealed.Error("Server Error"))
                    }
                    is Failure.NOKError -> {
                        uiState.postValue(SubmitResponseSealed.Error(failure.message ?: ""))
                    }
                    else -> uiState.postValue(SubmitResponseSealed.Error("Unknown Error"))
                }

            }, { result ->
                uiState.postValue(SubmitResponseSealed.Success(result))
            })
        }
    }
}