package com.sun.qakhadelivery.data.source.remote.schema.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpdateImage(
    @SerializedName("image") val image: String
) : Parcelable
