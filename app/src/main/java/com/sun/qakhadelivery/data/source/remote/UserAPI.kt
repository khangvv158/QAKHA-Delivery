package com.sun.qakhadelivery.data.source.remote

import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.data.source.remote.schema.request.ChangePasswordRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.ChangePasswordResponse
import com.sun.qakhadelivery.utils.Constants
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH

interface UserAPI {

    @GET("user")
    fun getUser(@Header(Constants.AUTHORIZATION) token: String?): Observable<User>

    @PATCH("user/change_password")
    fun changePassword(
        @Header(Constants.AUTHORIZATION) token: String?,
        @Body changePasswordRequest: ChangePasswordRequest
    ): Observable<ChangePasswordResponse>
}
