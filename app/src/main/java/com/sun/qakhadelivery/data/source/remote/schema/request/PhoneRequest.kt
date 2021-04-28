package com.sun.qakhadelivery.data.source.remote.schema.request

import com.google.gson.annotations.SerializedName

data class PhoneRequest(
        @SerializedName("phone_number") val phoneNumber: String,
        @SerializedName("type_user") var typeUser: Int = 1
)
