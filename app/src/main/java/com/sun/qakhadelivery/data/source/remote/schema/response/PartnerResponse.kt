package com.sun.qakhadelivery.data.source.remote.schema.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.data.model.Partner
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PartnerResponse(
    @SerializedName("partner") val partner: Partner,
    @SerializedName("avg_point") val avgPoint: Float,
    @SerializedName("number_of_reviews") val countReview: Float
) : Parcelable
