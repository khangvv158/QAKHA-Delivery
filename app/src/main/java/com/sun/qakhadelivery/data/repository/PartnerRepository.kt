package com.sun.qakhadelivery.data.repository

import com.sun.qakhadelivery.data.model.Feedback
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.data.model.TypePartner
import com.sun.qakhadelivery.data.source.remote.PartnerApi
import com.sun.qakhadelivery.data.source.remote.RetrofitClient
import com.sun.qakhadelivery.data.source.remote.schema.request.LocationRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.TypeRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.PartnerResponse
import io.reactivex.rxjava3.core.Observable

interface PartnerRepository {

    fun getTypes(): Observable<MutableList<TypePartner>>

    fun getPartners(): Observable<MutableList<Partner>>

    fun getPartnersByIdType(idType: Int): Observable<MutableList<Partner>>

    fun getFeedbackByIdPartner(idPartner: Int): Observable<Feedback>

    fun getSuggestPartnerNearby(locationRequest: LocationRequest): Observable<MutableList<Partner>>

    fun getSuggestPartnerBestRated(): Observable<MutableList<Partner>>

    fun getPartnerById(idPartner: Int): Observable<PartnerResponse>
}

class PartnerRepositoryImpl : PartnerRepository {

    private val client = RetrofitClient.getInstance().create(PartnerApi::class.java)

    override fun getTypes(): Observable<MutableList<TypePartner>> = client.getTypesPartner()

    override fun getPartners(): Observable<MutableList<Partner>> = client.getPartners()

    override fun getPartnersByIdType(idType: Int): Observable<MutableList<Partner>> =
        client.getPartnersByIdType(idType)

    override fun getFeedbackByIdPartner(idPartner: Int): Observable<Feedback> =
        client.getFeedbackByIdPartner(idPartner)

    override fun getSuggestPartnerNearby(locationRequest: LocationRequest): Observable<MutableList<Partner>> =
        client.getSuggestPartnerNearby(locationRequest)

    override fun getSuggestPartnerBestRated() = client.getSuggestPartnerBestRated()

    override fun getPartnerById(idPartner: Int) = client.getPartnerById(idPartner)

    companion object {

        @Volatile
        private var instance: PartnerRepositoryImpl? = null

        fun getInstance(): PartnerRepositoryImpl =
            instance ?: synchronized(this) {
                instance ?: PartnerRepositoryImpl().also {
                    instance = it
                }
            }
    }
}
