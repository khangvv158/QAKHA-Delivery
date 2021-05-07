package com.sun.qakhadelivery.data.source.remote.schema.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.data.model.DriverNearest
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.utils.Constants
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoryResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("address") val address: String,
    @SerializedName("subtotal") val subtotal: Float,
    @SerializedName("discount") val discount: Float = Constants.DEFAULT_FLOAT,
    @SerializedName("shipping_fee") val shippingFee: Float,
    @SerializedName("total") val total: Float,
    @SerializedName("status") val status: String,
    @SerializedName("type_checkout") val typeCheckout: String,
    @SerializedName("rate_status") val rateStatus: String,
    @SerializedName("description") val description: String = Constants.DEFAULT_STRING,
    @SerializedName("driver_id") val driverId: Int,
    @SerializedName("voucher_id") val voucherId: Int?,
    @SerializedName("partner_id") val partnerId: Int,
    @SerializedName("driver") val driver: DriverNearest,
    @SerializedName("partner") val partner: PartnerHistory
) : Parcelable {

    fun rate(): Boolean {
        return rateStatus == "rate"
    }
}
