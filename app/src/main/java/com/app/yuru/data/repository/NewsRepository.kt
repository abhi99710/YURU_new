package com.app.yuru.data.repository

import com.app.yuru.domain.entity.News
import com.app.yuru.coreandroid.exception.Failure
import com.app.yuru.coreandroid.functional.Either

interface NewsRepository {
    suspend fun getTopHeadlines(country: String, category: String): Either<Failure, List<News>>
}