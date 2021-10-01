package com.app.yuru.domain.entity

import com.google.gson.annotations.SerializedName

data class Result (

    @SerializedName("data") val data : Data,
    @SerializedName("message") val message : String
)