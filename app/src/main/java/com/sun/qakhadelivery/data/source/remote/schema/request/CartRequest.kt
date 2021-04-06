package com.sun.qakhadelivery.data.source.remote.schema.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartRequest(
    @SerializedName("product_id") val product_id: Int,
    @SerializedName("quantity") var quantity: Int,
    @SerializedName("partner_id") val partner_id: Int
) : Parcelable
