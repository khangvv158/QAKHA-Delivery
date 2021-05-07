package com.sun.qakhadelivery.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Voucher(
    @SerializedName("id") val id: Int,
    @SerializedName("code") val code: String,
    @SerializedName("discount") val discount: Float,
    @SerializedName("condition") val condition: Float?,
    @SerializedName("distance_condition") val distanceCondition: Float?,
    @SerializedName("expiry_date") val expiryDate: String,
    @SerializedName("usage_limit") val usageLimit: Int,
    @SerializedName("description") val description: String,
    @SerializedName("partner_id") val partnerId: Int
) : Parcelable
