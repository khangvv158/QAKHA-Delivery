package com.sun.qakhadelivery.data.source.remote.schema.response

import com.google.gson.annotations.SerializedName

data class RateDriver(
    @SerializedName("rated_driver") val rate: Boolean
)
