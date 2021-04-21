package com.sun.qakhadelivery.data.source.remote.schema.request

import com.google.gson.annotations.SerializedName

data class DistanceRequest(
    @SerializedName("partner_id") val partner_id: Int,
    @SerializedName("latitude") val latitude: Float,
    @SerializedName("longitude") val longitude: Float,
)
