package com.app.yuru.domain.entity

import com.app.yuru.ui.dto.NewsSourceDto

data class NewsSource(
    val id: String,
    val name: String
)

fun NewsSource.toDto(): NewsSourceDto {
    return NewsSourceDto(id, name)
}