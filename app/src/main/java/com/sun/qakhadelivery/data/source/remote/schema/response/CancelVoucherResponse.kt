package com.sun.qakhadelivery.data.source.remote.schema.response

import com.google.gson.annotations.SerializedName

data class CancelVoucherResponse(
    @SerializedName("message") val message: String,
    @SerializedName("subtotal") val subtotal: Float,
    @SerializedName("total") val total: Float
)
