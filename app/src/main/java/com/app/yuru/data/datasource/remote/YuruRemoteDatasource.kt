package com.app.yuru.data.datasource.remote

import com.app.yuru.domain.entity.News
import com.app.yuru.domain.entity.SignUpResponse

interface YuruRemoteDatasource {
    suspend fun getTopHeadlines(category: String, country: String): List<News>
    suspend fun register(fullName: String, email: String, password: String): SignUpResponse
}