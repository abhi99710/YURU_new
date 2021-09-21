package com.app.yuru.data.datasource.remote.service

import com.app.yuru.data.datasource.remote.model.TopHeadlineResponse
import com.app.yuru.domain.entity.SignUpResponse
import retrofit2.http.*

interface YuruApiService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String
    ): TopHeadlineResponse

    @FormUrlEncoded
    @POST("registration")
    suspend fun registration(
        @Field("full_name") fullName: String,
        @Field("email_id") emailId: String,
        @Field("password") password: String
    ): SignUpResponse
}