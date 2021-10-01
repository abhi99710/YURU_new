package com.app.yuru.ui.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.yuru.coreandroid.exception.Failure
import com.app.yuru.domain.entity.Json4Kotlin_Base
import com.app.yuru.domain.entity.QuestionResponseSubmit
import com.app.yuru.domain.usecase.QuestionUseCase
import com.app.yuru.domain.usecase.SubmitResponseUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SubmitResponseModel @Inject constructor(val submit: SubmitResponseUseCase) : ViewModel() {
    val submitLiveData = MutableLiveData<String>()
    fun uiState(): LiveData<SubmitResponseModel.SubmitReaponaeSealed> = uiState
    protected val uiState: MutableLiveData<SubmitResponseModel.SubmitReaponaeSealed> = MutableLiveData()

    sealed class SubmitReaponaeSealed {
        object Loading : SubmitResponseModel.SubmitReaponaeSealed()
        data class Success(val questionResponse: QuestionResponseSubmit) : SubmitResponseModel.SubmitReaponaeSealed()
        data class Error(val message: String) : SubmitResponseModel.SubmitReaponaeSealed()
    }

    fun submitResponse(user_id : String, answer : String) {
        viewModelScope.launch {
            uiState.postValue(SubmitResponseModel.SubmitReaponaeSealed.Loading)
            val newsList =
                submit.run(SubmitResponseUseCase.SubmitResponse(user_id, answer))
            newsList.fold({ failure ->
                when (failure) {
                    is Failure.ServerError -> {
                        uiState.postValue(SubmitResponseModel.SubmitReaponaeSealed.Error("Server Error"))
                    }
                    is Failure.NOKError -> {
                        uiState.postValue(SubmitResponseModel.SubmitReaponaeSealed.Error(failure.message ?: ""))
                    }
                    else -> uiState.postValue(SubmitResponseModel.SubmitReaponaeSealed.Error("Unknown Error"))
                }

            }, { result ->
                uiState.postValue(SubmitResponseModel.SubmitReaponaeSealed.Success(result))
            })
        }
    }
}