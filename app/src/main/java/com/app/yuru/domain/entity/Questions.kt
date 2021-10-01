package com.app.yuru.domain.entity

import com.google.gson.annotations.SerializedName


data class Questions (

	@SerializedName("question_id") val question_id : Int,
	@SerializedName("question") val question : String
)