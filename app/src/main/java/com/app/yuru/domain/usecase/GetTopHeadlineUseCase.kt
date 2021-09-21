package com.app.yuru.domain.usecase

import com.app.yuru.data.repository.YuruRepository
import com.app.yuru.domain.entity.News
import com.app.yuru.utility.ThreadInfoLogger
import com.app.yuru.coreandroid.exception.Failure
import com.app.yuru.coreandroid.functional.Either
import com.app.yuru.coreandroid.usecase.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTopHeadlineUseCase @Inject constructor(private val repository: YuruRepository) :
    UseCase<List<News>, GetTopHeadlineUseCase.TopHeadlineParam>() {

    override suspend fun run(params: TopHeadlineParam): Either<Failure, List<News>> =
        withContext(Dispatchers.IO) {
            ThreadInfoLogger.logThreadInfo("get top headline usecase")
            repository.getTopHeadlines(params.country, params.category)
        }

    data class TopHeadlineParam(
        val country: String,
        val category: String
    )


}