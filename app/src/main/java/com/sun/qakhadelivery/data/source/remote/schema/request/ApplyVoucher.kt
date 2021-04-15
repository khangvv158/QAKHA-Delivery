package com.sun.qakhadelivery.data.source.remote.schema.request

import com.google.gson.annotations.SerializedName

data class ApplyVoucher(
    @SerializedName("code") val code: String,
    @SerializedName("partner_id") val partner_id: Int
)
