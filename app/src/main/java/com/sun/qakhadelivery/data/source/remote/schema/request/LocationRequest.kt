package com.sun.qakhadelivery.data.source.remote.schema.request

import com.google.gson.annotations.SerializedName

data class LocationRequest(
    @SerializedName("latitude") val latitude: Float = 0f,
    @SerializedName("longitude") val longitude: Float = 0f,
)
