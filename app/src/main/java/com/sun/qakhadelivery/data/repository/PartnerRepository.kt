package com.sun.qakhadelivery.data.repository

import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.data.model.TypePartner
import com.sun.qakhadelivery.data.source.remote.PartnerApi
import com.sun.qakhadelivery.data.source.remote.RetrofitClient
import com.sun.qakhadelivery.data.source.remote.schema.request.TypeRequest
import io.reactivex.rxjava3.core.Observable

interface PartnerRepository {

    fun getTypes(): Observable<MutableList<TypePartner>>

    fun getPartners(): Observable<MutableList<Partner>>

    fun getPartnersByIdType(idType: Int): Observable<MutableList<Partner>>
}

class PartnerRepositoryImpl : PartnerRepository {

    private val client = RetrofitClient.getInstance().create(PartnerApi::class.java)

    override fun getTypes(): Observable<MutableList<TypePartner>> = client.getTypesPartner()

    override fun getPartners(): Observable<MutableList<Partner>> = client.getPartners()

    override fun getPartnersByIdType(idType : Int): Observable<MutableList<Partner>>
    = client.getPartnersByIdType(idType)

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
