package com.sun.qakhadelivery.data.source.remote.schema.response

import com.google.gson.annotations.SerializedName
import com.sun.qakhadelivery.data.model.Image

data class ChangePasswordResponse(
    @SerializedName("id") val idUser: Int,
    @SerializedName("email") val email: String,
    @SerializedName("image") val image: Image,
    @SerializedName("name") val name: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("coins") val coin: Float = 0f,
)
