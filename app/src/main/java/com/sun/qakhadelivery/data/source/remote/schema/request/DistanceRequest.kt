package com.sun.qakhadelivery.data.source.remote.schema.request

import com.google.gson.annotations.SerializedName

data class DistanceRequest(
    @SerializedName("partner_id") val partner_id: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
)
