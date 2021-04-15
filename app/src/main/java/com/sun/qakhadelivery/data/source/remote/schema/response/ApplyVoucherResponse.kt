package com.sun.qakhadelivery.data.source.remote.schema.response

import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.data.model.Voucher

data class ApplyVoucherResponse(
    @SerializedName("voucher") val voucher: Voucher,
    @SerializedName("subtotal") val subtotal: Float,
    @SerializedName("total_after_discount") val totalAfterDiscount: Float
)
