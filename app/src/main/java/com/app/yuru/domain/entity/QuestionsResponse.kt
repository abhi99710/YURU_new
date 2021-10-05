package com.app.yuru.domain.entity

import com.google.gson.annotations.SerializedName

data class QuestionsResponse (

	@SerializedName("valid") val valid : Boolean,
	@SerializedName("status") val status : String,
	@SerializedName("result") val result : Result
)