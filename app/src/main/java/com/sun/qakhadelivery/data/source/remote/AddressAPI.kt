package com.sun.qakhadelivery.data.source.remote

import com.sun.qakhadelivery.data.model.Address
import com.sun.qakhadelivery.data.source.remote.schema.request.AddressRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.MessageResponse
import com.sun.qakhadelivery.utils.Constants
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface AddressAPI {

    @GET("addresses")
    fun getAddresses(
        @Header(Constants.AUTHORIZATION) token: String?
    ): Single<MutableList<Address>>

    @POST("addresses")
    fun createAddress(
        @Body addressRequest: AddressRequest,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<Address>

    @DELETE("addresses/{address_id}")
    fun deleteAddress(
        @Path("address_id") idAddress: Int,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<MessageResponse>

    @PATCH("addresses/{address_id}")
    fun updateAddress(
        @Body addressRequest: AddressRequest,
        @Path("address_id") idAddress: Int,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<Address>
}
