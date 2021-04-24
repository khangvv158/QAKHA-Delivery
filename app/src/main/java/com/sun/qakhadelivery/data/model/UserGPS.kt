package com.sun.qakhadelivery.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserGPS(
    @SerializedName("latitude") val latitude: Float = 0f,
    @SerializedName("longitude") val longitude: Float = 0f
) : Parcelable
