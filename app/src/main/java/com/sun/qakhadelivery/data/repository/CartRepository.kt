package com.sun.qakhadelivery.data.repository

import com.sun.qakhadelivery.data.model.TokenAccess
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefs
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsKey
import com.sun.qakhadelivery.data.source.remote.CartAPI
import com.sun.qakhadelivery.data.source.remote.RetrofitClient
import com.sun.qakhadelivery.data.source.remote.schema.request.CartRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.RemoveCartRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.CartResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface CartRepository {

    fun getCart(partnerId: Int): Observable<CartResponse>

    fun createCart(cartRequest: CartRequest): Observable<CartResponse>

    fun removeCart(removeCartRequest: RemoveCartRequest): Observable<CartResponse>

    fun updateCart(cartRequest: CartRequest): Observable<CartResponse>

    fun clearCart(partnerId: Int): Completable
}

class CartRepositoryImpl private constructor(
    private val sharedPrefs: SharedPrefs
) : CartRepository {

    private val client = RetrofitClient.getInstance().create(CartAPI::class.java)

    override fun getCart(partnerId: Int): Observable<CartResponse> {
        return client.getCart(
            partnerId,
            sharedPrefs.get(SharedPrefsKey.TOKEN_KEY, TokenAccess::class.java).token
        )
    }

    override fun createCart(cartRequest: CartRequest): Observable<CartResponse>{
        return client.createCart(
            cartRequest,
            sharedPrefs.get(SharedPrefsKey.TOKEN_KEY, TokenAccess::class.java).token
        )
    }

    override fun removeCart(removeCartRequest: RemoveCartRequest): Observable<CartResponse> {
        return client.removeCart(
            removeCartRequest,
            sharedPrefs.get(SharedPrefsKey.TOKEN_KEY, TokenAccess::class.java).token
        )
    }

    override fun updateCart(cartRequest: CartRequest): Observable<CartResponse> {
        return client.updateCart(
            cartRequest,
            sharedPrefs.get(SharedPrefsKey.TOKEN_KEY, TokenAccess::class.java).token
        )
    }

    override fun clearCart(partnerId: Int): Completable {
        return client.clearCart(
            partnerId,
            sharedPrefs.get(SharedPrefsKey.TOKEN_KEY, TokenAccess::class.java).token
        )
    }

    companion object {

        private var instance: CartRepositoryImpl? = null

        fun getInstance(sharedPrefs: SharedPrefs): CartRepositoryImpl =
            instance ?: synchronized(this) {
                instance ?: CartRepositoryImpl(sharedPrefs).also {
                    instance = it
                }
            }
    }
}
