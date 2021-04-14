package com.sun.qakhadelivery.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductCart(
    @SerializedName("product_id") val productId: Int,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("partner_id") val partnerId: Int
) : Parcelable
