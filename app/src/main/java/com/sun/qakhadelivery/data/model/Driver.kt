package com.sun.qakhadelivery.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.utils.Constants.DEFAULT_FLOAT
import com.sun.qakhadelivery.utils.Constants.DEFAULT_STRING
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DriverNearest(
    @SerializedName("id") var id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String = DEFAULT_STRING,
    @SerializedName("address") val address: String = DEFAULT_STRING,
    @SerializedName("id_card") val id_card: String = DEFAULT_STRING,
    @SerializedName("phone_number") val phone_number: String = DEFAULT_STRING,
    @SerializedName("license_plate") val license_plate: String = DEFAULT_STRING,
    @SerializedName("image") val image: Image,
    @SerializedName("status") val status: String = DEFAULT_STRING,
    @SerializedName("coins") val coins: Float = DEFAULT_FLOAT,
    @SerializedName("latitude") val latitude: Float = DEFAULT_FLOAT,
    @SerializedName("longitude") val longitude: Float = DEFAULT_FLOAT
) : Parcelable
