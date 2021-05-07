package com.sun.qakhadelivery.data.source.remote.schema.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.data.model.Image
import com.sun.qakhadelivery.utils.Constants
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PartnerShippingResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("name") val name: String = Constants.DEFAULT_STRING,
    @SerializedName("address") val address: String = Constants.DEFAULT_STRING,
    @SerializedName("image") val image: Image?,
    @SerializedName("time_open") val timeOpen: String,
    @SerializedName("time_close") val timeClose: String,
    @SerializedName("latitude") val latitude: Float = Constants.DEFAULT_FLOAT,
    @SerializedName("longitude") val longitude: Float = Constants.DEFAULT_FLOAT,
    @SerializedName("distance") val distance: Float = Constants.DEFAULT_FLOAT,
    @SerializedName("average_point") val avgPoint: Float = Constants.DEFAULT_FLOAT,
) : Parcelable {

    fun toPartnerHistory(): PartnerHistory {
        return PartnerHistory(id, name, address, image, distance)
    }
}
