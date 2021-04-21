package com.sun.qakhadelivery.data.source.remote.schema.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DistanceResponse(
    @SerializedName("distance") val distance: Float,
    @SerializedName("shipping_fee") val shipping_fee: Float,
) : Parcelable
