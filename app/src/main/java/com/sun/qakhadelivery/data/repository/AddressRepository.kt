package com.sun.qakhadelivery.data.repository

import com.sun.qakhadelivery.data.model.Address
import com.sun.qakhadelivery.data.source.remote.AddressAPI
import com.sun.qakhadelivery.data.source.remote.RetrofitClient
import com.sun.qakhadelivery.data.source.remote.schema.request.AddressRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.MessageResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface AddressRepository {

    fun getAddresses(token: String): Single<MutableList<Address>>
    fun createAddress(addressRequest: AddressRequest, token: String): Observable<Address>
    fun updateAddress(
        addressRequest: AddressRequest,
        idAddress: Int,
        token: String
    ): Observable<Address>

    fun deleteAddress(idAddress: Int, token: String): Observable<MessageResponse>
}

class AddressRepositoryImpl : AddressRepository {

    private val client = RetrofitClient.getInstance().create(AddressAPI::class.java)

    override fun getAddresses(token: String): Single<MutableList<Address>> =
        client.getAddresses(token)

    override fun createAddress(addressRequest: AddressRequest, token: String): Observable<Address> =
        client.createAddress(addressRequest, token)

    override fun updateAddress(
        addressRequest: AddressRequest,
        idAddress: Int,
        token: String
    ): Observable<Address> =
        client.updateAddress(addressRequest, idAddress, token)

    override fun deleteAddress(idAddress: Int, token: String): Observable<MessageResponse> =
        client.deleteAddress(idAddress, token)

    companion object {

        private var instance: AddressRepositoryImpl? = null

        fun getInstance(): AddressRepositoryImpl =
            instance ?: synchronized(this) {
                instance ?: AddressRepositoryImpl().also {
                    instance = it
                }
            }
    }
}
