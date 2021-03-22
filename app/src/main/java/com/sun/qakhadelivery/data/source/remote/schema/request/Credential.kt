package com.sun.qakhadelivery.data.source.remote.schema.request

import com.google.gson.annotations.SerializedName

data class Credential(
        @SerializedName("email") val email: String,
        @SerializedName("password") val password: String,
        @SerializedName("password_confirmation") val passwordConfirmation: String
)
