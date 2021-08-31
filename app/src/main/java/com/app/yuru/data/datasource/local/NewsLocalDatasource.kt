package com.app.yuru.data.datasource.local

import com.app.yuru.domain.entity.News

interface NewsLocalDatasource {
   suspend fun insertNews(news: List<News>)
   suspend fun getAllNews(): List<News>
}
