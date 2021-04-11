package com.sun.qakhadelivery.data.source.remote.schema.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class RemoveCartRequest(
    @SerializedName("partner_id") val partner_id: Int,
    @SerializedName("product_id") val product_id: Int
) : Parcelable
