package com.sun.qakhadelivery.data.source.remote

import com.sun.qakhadelivery.data.source.remote.schema.response.MessageResponse
import com.sun.qakhadelivery.data.model.TokenAccess
import com.sun.qakhadelivery.data.source.remote.schema.request.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface SignAPI {

    @POST("sign_in")
    fun signIn(@Body parameterName: Credential): Observable<TokenAccess>

    @POST("sign_up")
    fun signUp(@Body register: Register): Observable<MessageResponse>

    @POST("check_email")
    fun checkEmail(@Body emailRequest: EmailRequest): Observable<Boolean>

    @POST("check_phone_number")
    fun checkPhoneNumber(@Body phoneRequest: PhoneRequest): Observable<Boolean>

    @POST("passwords/forgot")
    fun forgotPassword(@Body emailRequest: EmailRequest): Observable<MessageResponse>

    @POST("passwords/reset")
    fun resetPasswordByVerificationCode(
        @Body resetPasswordRequest: ResetPasswordRequest
    ): Observable<MessageResponse>

    @POST("activated_account")
    fun activateAccount(
        @Body activateRequest: ActivateRequest
    ): Observable<MessageResponse>

    @POST("user/confirmation")
    fun resendCodeActivate(@Body emailRequest: EmailRequest): Observable<MessageResponse>
}
