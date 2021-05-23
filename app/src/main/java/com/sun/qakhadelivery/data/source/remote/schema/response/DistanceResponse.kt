package com.sun.qakhadelivery.data.source.remote.schema.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.utils.Constants.DEFAULT_FLOAT
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DistanceResponse(
    @SerializedName("distance") val distance: Float,
    @SerializedName("shipping_fee") val shipping_fee: Float = DEFAULT_FLOAT,
) : Parcelable {

    fun isMaxDistance() = distance > MAX_DISTANCE

    companion object {
        private const val MAX_DISTANCE = 15F
    }
}
