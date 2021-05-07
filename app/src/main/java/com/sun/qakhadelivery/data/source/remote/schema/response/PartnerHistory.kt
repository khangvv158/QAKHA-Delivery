package com.sun.qakhadelivery.data.source.remote.schema.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.data.model.Image
import com.sun.qakhadelivery.utils.Constants
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PartnerHistory(
    @SerializedName("id") var id: Int,
    @SerializedName("name") val name: String = Constants.DEFAULT_STRING,
    @SerializedName("address") val address: String = Constants.DEFAULT_STRING,
    @SerializedName("image") val image: Image?,
    @SerializedName("distance") val distance: Float = Constants.DEFAULT_FLOAT
) : Parcelable
