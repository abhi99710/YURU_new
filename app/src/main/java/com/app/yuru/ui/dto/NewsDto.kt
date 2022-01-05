package com.app.yuru.ui.dto

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsDto(
    val source: NewsSourceDto,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(NewsSourceDto::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(source, flags)
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(urlToImage)
        parcel.writeString(publishedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsDto> {
        override fun createFromParcel(parcel: Parcel): NewsDto {
            return NewsDto(parcel)
        }

        override fun newArray(size: Int): Array<NewsDto?> {
            return arrayOfNulls(size)
        }
    }
}