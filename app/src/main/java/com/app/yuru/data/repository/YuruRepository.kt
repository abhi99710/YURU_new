package com.app.yuru.data.repository

import com.app.yuru.coreandroid.exception.Failure
import com.app.yuru.coreandroid.functional.Either
import com.app.yuru.domain.entity.News
import com.app.yuru.domain.entity.SignUpResponse

interface YuruRepository {
    suspend fun getTopHeadlines(country: String, category: String): Either<Failure, List<News>>
    suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): Either<Failure, SignUpResponse>
}