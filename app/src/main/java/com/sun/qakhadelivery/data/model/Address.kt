package com.sun.qakhadelivery.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    @SerializedName("id") val idAddress: Int,
    @SerializedName("name") val name: String,
    @SerializedName("latitude") val latitude: Float,
    @SerializedName("longitude") val longitude: Float,
    @SerializedName("user_id") val userId: Int
) : Parcelable
