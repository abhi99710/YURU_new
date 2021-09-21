package com.app.yuru.ui.login

import androidx.lifecycle.viewModelScope
import com.app.yuru.coreandroid.BaseViewModel
import com.app.yuru.coreandroid.exception.Failure
import com.app.yuru.domain.entity.SignUpResponse
import com.app.yuru.domain.usecase.SignUpUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase) :
    BaseViewModel<SignUpViewModel.SignUpState>() {

    sealed class SignUpState {
        object Loading : SignUpState()
        data class Success(val signUpResponse: SignUpResponse) : SignUpState()
        data class Error(val message: String) : SignUpState()
    }

    fun register(fullName: String, email: String, password: String) {
        viewModelScope.launch {
            uiState.postValue(SignUpState.Loading)
            val newsList =
                signUpUseCase.run(SignUpUseCase.SignUpParams(fullName, email, password))
            newsList.fold({ failure ->
                when (failure) {
                    is Failure.ServerError -> {
                        uiState.postValue(SignUpState.Error("Server Error"))
                    }
                    is Failure.NOKError -> {
                        uiState.postValue(SignUpState.Error(failure.message ?: ""))
                    }
                    else -> uiState.postValue(SignUpState.Error("Unknown Error"))
                }

            }, { result ->
                uiState.postValue(SignUpState.Success(result))
            })
        }
    }

}