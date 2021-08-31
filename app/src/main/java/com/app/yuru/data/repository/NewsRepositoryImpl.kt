package com.app.yuru.data.repository

import com.github.ajalt.timberkt.d
import com.app.yuru.data.datasource.local.NewsLocalDatasource
import com.app.yuru.data.datasource.remote.NewsRemoteDatasource
import com.app.yuru.domain.entity.News
import com.app.yuru.utility.ThreadInfoLogger
import com.app.yuru.coreandroid.exception.Failure
import com.app.yuru.coreandroid.functional.Either
import com.app.yuru.coreandroid.network.NetworkChecker
import java.io.IOException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
        private val remote: NewsRemoteDatasource,
        private val local: NewsLocalDatasource,
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

                local.insertNews(response)

                Either.Right(response)
            } else {
                d { "connection : disconnect" }
                // not connected
                val localNews = local.getAllNews()
                d { "get data from local: ${localNews.size}" }
                if (localNews.isEmpty()) {
                    Either.Left(Failure.LocalDataNotFound)
                } else {
                    Either.Right(localNews)
                }
            }
        } catch (ex: Exception) {
            Either.Left(Failure.ServerError(ex.localizedMessage))
        }
    }
}
