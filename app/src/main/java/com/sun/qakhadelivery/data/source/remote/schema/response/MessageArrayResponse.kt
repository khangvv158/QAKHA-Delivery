package com.sun.qakhadelivery.data.source.remote.schema.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MessageArrayResponse(
    @SerializedName("message") val message: ArrayList<String>
) : Parcelable
