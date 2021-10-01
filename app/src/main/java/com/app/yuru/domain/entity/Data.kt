package com.app.yuru.domain.entity

import com.google.gson.annotations.SerializedName

data class Data (

	@SerializedName("title") val title : String,
	@SerializedName("description") val description : String,
	@SerializedName("questions") val questions : List<Questions>
)