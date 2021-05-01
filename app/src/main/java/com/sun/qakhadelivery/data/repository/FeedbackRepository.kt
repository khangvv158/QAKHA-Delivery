package com.sun.qakhadelivery.data.repository

import com.sun.qakhadelivery.data.source.remote.FeedbackAPI
import com.sun.qakhadelivery.data.source.remote.RetrofitClient
import com.sun.qakhadelivery.data.source.remote.schema.request.RateDriverRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.RatePartnerRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.RateDriver
import com.sun.qakhadelivery.data.source.remote.schema.response.RateDriverResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.RatePartnerResponse
import io.reactivex.rxjava3.core.Observable

interface FeedbackRepository {

    fun rateDriver(
        rateDriverRequest: RateDriverRequest,
        token: String
    ): Observable<RateDriverResponse>

    fun ratePartner(
        ratePartnerRequest: RatePartnerRequest,
        token: String
    ): Observable<RatePartnerResponse>

    fun checkFeedBack(orderId: Int, token: String): Observable<RateDriver>
}

class FeedbackRepositoryImpl : FeedbackRepository {

    private val client = RetrofitClient.getInstance().create(FeedbackAPI::class.java)

    override fun rateDriver(
        rateDriverRequest: RateDriverRequest,
        token: String
    ): Observable<RateDriverResponse> {
        return client.rateDriver(rateDriverRequest, token)
    }

    override fun ratePartner(
        ratePartnerRequest: RatePartnerRequest,
        token: String
    ): Observable<RatePartnerResponse> {
        return client.ratePartner(ratePartnerRequest, token)
    }

    override fun checkFeedBack(orderId: Int, token: String): Observable<RateDriver> {
        return client.checkRateDriver(orderId, token)
    }

    companion object {

        private var instance: FeedbackRepositoryImpl? = null

        fun getInstance(): FeedbackRepositoryImpl =
            instance ?: synchronized(this) {
                instance ?: FeedbackRepositoryImpl().also {
                    instance = it
                }
            }
    }
}
