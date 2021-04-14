package com.sun.qakhadelivery.data.source.remote.schema.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.data.model.ProductCart
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartResponse(
    @SerializedName("carts") val products: MutableList<ProductCart>,
    @SerializedName("total_price_cart") val total: Float,
) : Parcelable
