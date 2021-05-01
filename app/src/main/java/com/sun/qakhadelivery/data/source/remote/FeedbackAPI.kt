package com.sun.qakhadelivery.data.source.remote

import com.sun.qakhadelivery.data.source.remote.schema.request.RateDriverRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.RatePartnerRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.RateDriver
import com.sun.qakhadelivery.data.source.remote.schema.response.RateDriverResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.RatePartnerResponse
import com.sun.qakhadelivery.utils.Constants
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface FeedbackAPI {

    @POST("orders/feedbacks")
    fun rateDriver(
        @Body rateDriverRequest: RateDriverRequest,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<RateDriverResponse>

    @POST("orders/feedbacks")
    fun ratePartner(
        @Body ratePartner: RatePartnerRequest,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<RatePartnerResponse>

    @GET("feedbacks/check_feedback_driver")
    fun checkRateDriver(
        @Query("order_id") orderId: Int,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<RateDriver>
}
