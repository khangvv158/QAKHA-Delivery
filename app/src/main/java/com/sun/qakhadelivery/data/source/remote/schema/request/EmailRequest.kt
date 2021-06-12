package com.sun.qakhadelivery.data.source.remote.schema.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EmailRequest(
    @SerializedName("email") val email: String,
    @SerializedName("type_user") var typeUser: Int = 1
) : Parcelable
