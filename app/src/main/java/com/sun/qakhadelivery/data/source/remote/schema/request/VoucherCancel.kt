package com.sun.qakhadelivery.data.source.remote.schema.request

import com.google.gson.annotations.SerializedName

data class VoucherCancel(
    @SerializedName("voucher_id") val voucher_id: Int,
    @SerializedName("partner_id") val partner_id: Int
)
