package com.sun.qakhadelivery.data.model

import com.google.gson.annotations.SerializedName

data class MessageError(
        @SerializedName("message") val message: String
)
