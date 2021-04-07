package com.sun.qakhadelivery.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.utils.Constants.DEFAULT_STRING
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    @SerializedName("url") val imageUrl: String = DEFAULT_STRING
) : Parcelable
