package com.sun.qakhadelivery.data.source.remote

import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.data.source.remote.schema.request.*
import com.sun.qakhadelivery.data.source.remote.schema.response.ChangePasswordResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.MessageResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.VerifyEmail
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

    @PATCH("user")
    fun updateImage(
        @Body updateImage: UpdateImage,
        @Header(Constants.AUTHORIZATION) token: String?,
    ): Observable<User>

    @PATCH("user")
    fun updateUsername(
        @Body updateUsername: UpdateUsername,
        @Header(Constants.AUTHORIZATION) token: String?,
    ): Observable<User>

    @PATCH("user")
    fun updatePhoneNumber(
        @Body updatePhone: UpdatePhone,
        @Header(Constants.AUTHORIZATION) token: String?,
    ): Observable<User>

    @PATCH("user")
    fun updateEmail(
        @Body updateEmail: UpdateEmail,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<User>

    @PATCH("user/change_email")
    fun verifyEmail(
        @Body verifyEmail: VerifyEmail,
        @Header(Constants.AUTHORIZATION) token: String?
    ): Observable<MessageResponse>
}
