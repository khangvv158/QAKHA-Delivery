package com.sun.qakhadelivery.data.repository

import com.sun.qakhadelivery.data.source.remote.CartAPI
import com.sun.qakhadelivery.data.source.remote.RetrofitClient
import com.sun.qakhadelivery.data.source.remote.schema.request.CartRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.RemoveCartRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.CartResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface CartRepository {

    fun getCart(partnerId: Int, token: String): Observable<CartResponse>

    fun createCart(cartRequest: CartRequest, token: String): Observable<CartResponse>

    fun removeCart(removeCartRequest: RemoveCartRequest, token: String): Observable<CartResponse>

    fun updateCart(cartRequest: CartRequest, token: String): Observable<CartResponse>

    fun clearCart(partnerId: Int, token: String): Completable
}

class CartRepositoryImpl private constructor() : CartRepository {

    private val client = RetrofitClient.getInstance().create(CartAPI::class.java)

    override fun getCart(partnerId: Int, token: String): Observable<CartResponse> {
        return client.getCart(partnerId, token)
    }

    override fun createCart(cartRequest: CartRequest, token: String): Observable<CartResponse> {
        return client.createCart(cartRequest, token)
    }

    override fun removeCart(
        removeCartRequest: RemoveCartRequest,
        token: String
    ): Observable<CartResponse> {
        return client.removeCart(removeCartRequest, token)
    }

    override fun updateCart(cartRequest: CartRequest, token: String): Observable<CartResponse> {
        return client.updateCart(cartRequest, token)
    }

    override fun clearCart(partnerId: Int, token: String): Completable {
        return client.clearCart(partnerId, token)
    }

    companion object {

        private var instance: CartRepositoryImpl? = null

        fun getInstance(): CartRepositoryImpl =
            instance ?: synchronized(this) {
                instance ?: CartRepositoryImpl().also {
                    instance = it
                }
            }
    }
}
