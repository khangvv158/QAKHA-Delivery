package com.sun.qakhadelivery.data.source.remote

import com.sun.qakhadelivery.data.model.Feedback
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.data.model.TypePartner
import com.sun.qakhadelivery.data.source.remote.schema.request.LocationRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.PartnerResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PartnerApi {

    @GET("types")
    fun getTypesPartner(): Observable<MutableList<TypePartner>>

    @GET("partners")
    fun getPartners(): Observable<MutableList<Partner>>

    @GET("types/partners")
    fun getPartnersByIdType(@Query("type_id") idType: Int): Observable<MutableList<Partner>>

    @GET("feedbacks/partner")
    fun getFeedbackByIdPartner(@Query("partner_id") idPartner: Int): Observable<Feedback>

    @POST("suggest_partners_nearby")
    fun getSuggestPartnerNearby(
        @Body locationRequest: LocationRequest
    ): Observable<MutableList<Partner>>

    @GET("suggest_partners")
    fun getSuggestPartnerBestRated(): Observable<MutableList<Partner>>

    @GET("partner")
    fun getPartnerById(@Query("id") idPartner: Int): Observable<PartnerResponse>
}
