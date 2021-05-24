package com.sun.qakhadelivery.data.model

import android.os.Parcelable
import com.sun.qakhadelivery.data.source.remote.schema.response.HistoryResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.OrderDetailsResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.RateDriver
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderDetailMerge(
    val orderDetailsResponse: OrderDetailsResponse,
    val RateDriver: RateDriver,
    val historyResponse: HistoryResponse
) : Parcelable
