package com.sun.qakhadelivery.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Voucher(
    @SerializedName("id") val id: String,
    @SerializedName("code") val code: String,
    @SerializedName("discount") val discount: Float,
    @SerializedName("condition") val condition: String,
    @SerializedName("expiry_date") val expiryDate: Date,
    @SerializedName("usage_limit") val usageLimit: Int,
    @SerializedName("description") val description: String,
    @SerializedName("partner_id") val partnerId: String
) : Parcelable
