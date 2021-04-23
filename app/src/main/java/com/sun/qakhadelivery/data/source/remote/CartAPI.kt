package com.sun.qakhadelivery.data.source.remote

import com.sun.qakhadelivery.data.source.remote.schema.request.CartRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.RemoveCartRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.CartResponse
import com.sun.qakhadelivery.utils.Constants.AUTHORIZATION
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface CartAPI {

    @GET("cart")
    fun getCart(
        @Query("partner_id") partnerId: Int,
        @Header(AUTHORIZATION) token: String?
    ): Observable<CartResponse>

    @POST("carts")
    fun createCart(
        @Body cartRequest: CartRequest,
        @Header(AUTHORIZATION) token: String?
    ): Observable<CartResponse>

    @HTTP(method = "DELETE", hasBody = true, path = "carts")
    fun removeCart(
        @Body removeCartRequest: RemoveCartRequest,
        @Header(AUTHORIZATION) token: String?
    ): Observable<CartResponse>

    @PUT("carts")
    fun updateCart(
        @Body cartRequest: CartRequest,
        @Header(AUTHORIZATION) token: String?
    ): Observable<CartResponse>

    @DELETE("clear_cart")
    fun clearCart(
        @Query("partner_id") partnerId: Int,
        @Header(AUTHORIZATION) token: String?
    ): Completable
}
