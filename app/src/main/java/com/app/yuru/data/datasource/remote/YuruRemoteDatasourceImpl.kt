package com.app.yuru.data.datasource.remote

import com.app.yuru.data.datasource.remote.model.toNewsList
import com.app.yuru.data.datasource.remote.service.YuruApiService
import com.app.yuru.domain.entity.News
import com.app.yuru.domain.entity.SignUpResponse
import javax.inject.Inject

class YuruRemoteDatasourceImpl @Inject constructor(private val services: YuruApiService) :
    YuruRemoteDatasource {

    override suspend fun getTopHeadlines(category: String, country: String): List<News> {
        return services.getTopHeadlines(country, category).toNewsList()
    }

    override suspend fun register(fullName: String, email: String, password: String): SignUpResponse {
        return services.registration(fullName, email, password)
    }

}