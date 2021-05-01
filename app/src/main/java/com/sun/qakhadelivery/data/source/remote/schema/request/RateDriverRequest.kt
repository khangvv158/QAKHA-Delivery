package com.sun.qakhadelivery.data.source.remote.schema.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RateDriverRequest(
    @SerializedName("order_id") val order_id: Int,
    @SerializedName("content") val content: String,
    @SerializedName("point") val point: Int,
    @SerializedName("driver_id") val driver_id: Int
) : Parcelable
