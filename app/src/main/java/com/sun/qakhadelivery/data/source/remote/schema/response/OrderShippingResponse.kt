package com.sun.qakhadelivery.data.source.remote.schema.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.data.model.Image
import com.sun.qakhadelivery.utils.Constants
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderShippingResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: Image?,
    @SerializedName("phone_number") val phone_number: String,
    @SerializedName("address") val address: String,
    @SerializedName("subtotal") val subtotal: Float,
    @SerializedName("discount") val discount: Float = Constants.DEFAULT_FLOAT,
    @SerializedName("shipping_fee") val shipping_fee: Float,
    @SerializedName("total") val total: Float,
    @SerializedName("status") val status: String,
    @SerializedName("type_checkout") val type_checkout: String,
    @SerializedName("driver_id") val driver_id: Int,
    @SerializedName("partner_id") val partner_id: Int
) : Parcelable
