package com.sun.qakhadelivery.data.source.remote.schema.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VoucherDistance(
    @SerializedName("code") val code: String,
    @SerializedName("partner_id") val partner_id: Int,
    @SerializedName("distance") val distance: Float
) : Parcelable
