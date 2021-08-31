package com.app.yuru.data.datasource.remote

import com.app.yuru.domain.entity.News

interface NewsRemoteDatasource {
    suspend fun getTopHeadlines(category: String, country: String): List<News>
}