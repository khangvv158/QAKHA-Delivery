package com.sun.qakhadelivery.data.model

data class Order(
        val id: Int?,
        val image: String?,
        val date: String?,
        val partner: Partner?,
        val status: Int?,
        val total: Float?
)
