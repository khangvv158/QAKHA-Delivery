package com.sun.qakhadelivery.data.source.remote

import com.sun.qakhadelivery.data.model.User
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface UserAPI {

    @GET("user")
    fun getUser(): Observable<User>
}
