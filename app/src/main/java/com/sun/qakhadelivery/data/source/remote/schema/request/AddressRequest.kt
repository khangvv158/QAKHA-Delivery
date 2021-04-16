package com.sun.qakhadelivery.data.source.remote.schema.request

import com.google.gson.annotations.SerializedName

data class AddressRequest(
    @SerializedName("name") val name: String,
    @SerializedName("latitude") val latitude: Float,
    @SerializedName("longitude") val longitude: Float
)
