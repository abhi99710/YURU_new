package com.app.yuru.data.datasource.remote.model

import com.app.yuru.domain.entity.News

data class TopHeadlineResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsResponse>
)

fun TopHeadlineResponse.toNewsList(): List<News> {
    val listNews = mutableListOf<News>()
    articles.map {
        listNews.add(toNews((it)))
    }
    return listNews
}

