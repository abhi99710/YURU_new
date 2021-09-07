package com.app.yuru.data.repository

import com.app.yuru.coreandroid.exception.Failure
import com.app.yuru.coreandroid.functional.Either
import com.app.yuru.coreandroid.network.NetworkChecker
import com.app.yuru.data.datasource.remote.NewsRemoteDatasource
import com.app.yuru.domain.entity.News
import com.app.yuru.utility.ThreadInfoLogger
import com.github.ajalt.timberkt.d
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val remote: NewsRemoteDatasource,
    private val networkChecker: NetworkChecker
) : NewsRepository {

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
}
