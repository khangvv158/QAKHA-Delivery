package com.sun.qakhadelivery.data.source.remote.schema.request

import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.data.model.Address
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.data.source.remote.schema.response.DistanceResponse
import com.sun.qakhadelivery.utils.TimeHelper

data class OrderRequest(
    @SerializedName("name") val name: String,
    @SerializedName("phone_number") val phone_number: String,
    @SerializedName("address") val address: String,
    @SerializedName("partner_id") val partner_id: Int,
    @SerializedName("shipping_fee") val shipping_fee: Float,
    @SerializedName("type_checkout") val type_checkout: String,
    @SerializedName("latitude") val latitude: Float,
    @SerializedName("longitude") val longitude: Float,
) {

    constructor(
        user: User,
        address: Address,
        partner: Partner,
        distanceResponse: DistanceResponse,
        type_checkout: String
    ) : this(
        user.name,
        user.phoneNumber,
        address.name,
        partner.id,
        distanceResponse.shipping_fee,
        type_checkout,
        address.latitude,
        address.longitude
    )
}
