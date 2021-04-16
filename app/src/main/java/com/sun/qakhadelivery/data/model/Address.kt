package com.sun.qakhadelivery.data.model

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("id") val idAddress: Int,
    @SerializedName("name") val name: String,
    @SerializedName("latitude") val latitude: Float,
    @SerializedName("longitude") val longitude: Float,
    @SerializedName("user_id") val userId: Int
)
