package com.app.yuru.ui.homepage

import androidx.lifecycle.viewModelScope
import com.app.yuru.domain.entity.News
import com.app.yuru.domain.usecase.GetTopHeadlineUseCase
import com.app.yuru.utility.ThreadInfoLogger
import com.app.yuru.coreandroid.BaseViewModel
import com.app.yuru.coreandroid.exception.Failure
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListNewsViewModel @Inject constructor(private val getTopHeadlineUseCase: GetTopHeadlineUseCase) :
    BaseViewModel<ListNewsViewModel.ListNewsState>() {

    sealed class ListNewsState {
        object Loading : ListNewsState()
        data class NewsLoaded(val news: List<News>) : ListNewsState()
        data class Error(val message: String) : ListNewsState()
    }

    fun getTopHeadlinesByCountry(country: String, category: String) {
        viewModelScope.launch {
            uiState.postValue(ListNewsState.Loading)

            ThreadInfoLogger.logThreadInfo("get top headlines viewmodel")
            val newsList =
                getTopHeadlineUseCase.run(GetTopHeadlineUseCase.TopHeadlineParam(country, category))

            newsList.fold({ failure ->
                when (failure) {
                    is Failure.ServerError -> {
                        uiState.postValue(ListNewsState.Error("Server Error"))
                    }
                    else -> uiState.postValue(ListNewsState.Error("Unknown Error"))
                }

            }, { result ->
                if (!result.isNullOrEmpty()) {
                    uiState.postValue(ListNewsState.NewsLoaded(result))
                }
            })
        }
    }

}