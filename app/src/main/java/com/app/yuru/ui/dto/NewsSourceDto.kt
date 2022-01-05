package com.app.yuru.ui.dto

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsSourceDto(
    val id: String,
    val name: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsSourceDto> {
        override fun createFromParcel(parcel: Parcel): NewsSourceDto {
            return NewsSourceDto(parcel)
        }

        override fun newArray(size: Int): Array<NewsSourceDto?> {
            return arrayOfNulls(size)
        }
    }
}
