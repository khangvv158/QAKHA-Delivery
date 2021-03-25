package com.sun.qakhadelivery.data.source.remote

import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.utils.Constants
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Header

interface UserAPI {

    @GET("user")
    fun getUser(@Header(Constants.AUTHORIZATION) token: String?): Observable<User>
}
