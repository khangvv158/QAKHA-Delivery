package com.sun.qakhadelivery.data.source.remote

import com.sun.qakhadelivery.data.model.TypePartner
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface PartnerApi {

    @GET("types")
    fun getTypesPartner(): Observable<MutableList<TypePartner>>
}
