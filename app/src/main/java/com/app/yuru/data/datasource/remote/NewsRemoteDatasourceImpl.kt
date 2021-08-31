package com.app.yuru.data.datasource.remote

import com.app.yuru.data.datasource.remote.model.toNewsList
import com.app.yuru.data.datasource.remote.service.NewsApiServices
import com.app.yuru.domain.entity.News
import javax.inject.Inject

class NewsRemoteDatasourceImpl @Inject constructor(private val services: NewsApiServices) :
    NewsRemoteDatasource {

    override suspend fun getTopHeadlines(category: String, country: String): List<News> {
        return services.getTopHeadlines(country, category).toNewsList()
    }
}