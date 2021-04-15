package com.sun.qakhadelivery.data.source.remote.schema.request

import com.google.gson.annotations.SerializedName

data class VoucherRequest(
    @SerializedName("partner_id") val partnerId: String,
)
