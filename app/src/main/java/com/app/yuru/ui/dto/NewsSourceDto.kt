package com.app.yuru.ui.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsSourceDto(
    val id: String,
    val name: String
) : Parcelable
