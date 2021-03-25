package com.sun.qakhadelivery.data.source.remote.schema.request

import com.google.gson.annotations.SerializedName

data class Register(
        @SerializedName("email") val email: String,
        @SerializedName("password") val password: String,
        @SerializedName("password_confirmation") val password_confirmation: String,
        @SerializedName("phone_number") val phone_number: String,
        @SerializedName("name") val name: String
)
