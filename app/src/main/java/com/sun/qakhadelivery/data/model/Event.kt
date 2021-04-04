package com.sun.qakhadelivery.data.model

data class Event<T>(
        val keyEvent: String,
        val obj: T
)
