package com.app.yuru.domain.entity

data class SignUpData(
    val accesstoken: String,
    val email_id: String,
    val full_name: String,
    val registration_date: String,
    val user_id: Int
)