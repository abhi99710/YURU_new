package com.app.yuru.data.repository

import com.app.yuru.coreandroid.exception.Failure
import com.app.yuru.coreandroid.functional.Either
import com.app.yuru.coreandroid.network.NetworkChecker
import com.app.yuru.data.datasource.remote.YuruRemoteDatasource
import com.app.yuru.domain.entity.ErrorResponse
import com.app.yuru.domain.entity.News
import com.app.yuru.domain.entity.SignUpResponse
import com.app.yuru.utility.ThreadInfoLogger
import com.github.ajalt.timberkt.d
import com.google.gson.Gson
import retrofit2.HttpException
import javax.inject.Inject

class YuruRepositoryImpl @Inject constructor(
    private val remote: YuruRemoteDatasource,
    private val networkChecker: NetworkChecker
) : YuruRepository {

    override suspend fun getTopHeadlines(
        country: String,
        category: String
    ): Either<Failure, List<News>> {
        return try {
            if (networkChecker.isNetworkConnected()) {
                d { "connection : connect to internet" }
                // connected to internet
                ThreadInfoLogger.logThreadInfo("get top headlines repository")
                val response = remote.getTopHeadlines(category = category, country = country)

                Either.Right(response)
            } else {
                Either.Left(Failure.NetworkException)
            }
        } catch (ex: Exception) {
            Either.Left(Failure.ServerError(ex.localizedMessage))
        }
    }

    override suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): Either<Failure, SignUpResponse> {
        return try {
            if (networkChecker.isNetworkConnected()) {
                val response = remote.register(fullName, email, password)
                Either.Right(response)
            } else {
                Either.Left(Failure.NetworkException)
            }
        } catch (ex: Exception) {
            if (ex is HttpException) {
                val error = Gson().fromJson(
                    ex.response()?.errorBody()?.string(),
                    ErrorResponse::class.java
                )
                Either.Left(Failure.NOKError(error.result.message))
            } else {
                Either.Left(Failure.ServerError(ex.localizedMessage))
            }
        }
    }
}
