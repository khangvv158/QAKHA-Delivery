package com.sun.qakhadelivery.data.source.remote.schema.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.data.model.Order
import com.sun.qakhadelivery.data.model.OrderDetails
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderDetailsResponse(
    @SerializedName("order") val order: Order,
    @SerializedName("order_details") val orderDetails: MutableList<OrderDetails>
) : Parcelable
