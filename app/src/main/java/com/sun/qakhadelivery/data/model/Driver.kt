package com.sun.qakhadelivery.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DriverNearest(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("address") val address: String,
    @SerializedName("id_card") val id_card: String,
    @SerializedName("phone_number") val phone_number: String,
    @SerializedName("license_plate") val license_plate: String,
    @SerializedName("image") val image: Image,
    @SerializedName("status") val status: String,
    @SerializedName("coins") val coins: Float,
    @SerializedName("latitude") val latitude: Float,
    @SerializedName("longitude") val longitude: Float
) : Parcelable
