package com.sun.qakhadelivery.data.source.remote.schema.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.data.model.Image
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RatePartnerResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("content") val content: String,
    @SerializedName("image") val image: Image,
    @SerializedName("point") val point: Int,
    @SerializedName("order_id") val orderId: Int,
    @SerializedName("driver_id") val driverId: Int,
    @SerializedName("partner_id") val partnerId: Int
) : Parcelable
