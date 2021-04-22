package com.sun.qakhadelivery.data.repository

import com.sun.qakhadelivery.data.source.remote.schema.response.MessageResponse
import com.sun.qakhadelivery.data.model.TokenAccess
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefs
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsKey
import com.sun.qakhadelivery.data.source.remote.RetrofitClient
import com.sun.qakhadelivery.data.source.remote.SignAPI
import com.sun.qakhadelivery.data.source.remote.schema.request.*
import com.sun.qakhadelivery.utils.Constants
import io.reactivex.rxjava3.core.Observable

interface SignRepository {

    fun signIn(
        email: String,
        password: String,
    ): Observable<TokenAccess>

    fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String,
        phoneNumber: String,
        name: String
    ): Observable<MessageResponse>

    fun checkEmail(emailRequest: EmailRequest): Observable<Boolean>

    fun checkPhoneNumber(phoneRequest: PhoneRequest): Observable<Boolean>

    fun forgotPassword(emailRequest: EmailRequest): Observable<MessageResponse>

    fun resetPasswordByVerificationCode(resetPasswordRequest: ResetPasswordRequest): Observable<MessageResponse>

    fun activateAccount(activateRequest: ActivateRequest): Observable<MessageResponse>
}

class SignRepositoryImpl private constructor(
    private val sharedPrefs: SharedPrefs
) : SignRepository {

    private val client = RetrofitClient.getInstance().create(SignAPI::class.java)

    override fun signIn(
        email: String,
        password: String
    ): Observable<TokenAccess> =
        client.signIn(
            Credential(
                email,
                password
            )
        ).doOnNext {
            if (it.role == Constants.ROLE_MEMBER || it.role == Constants.ROLE_ADMIN) {
                sharedPrefs.put(SharedPrefsKey.TOKEN_KEY, it)
            }
        }

    override fun signUp(
        email: String,
        password: String,
        passwordConfirmation: String,
        phoneNumber: String,
        name: String
    ): Observable<MessageResponse> =
        client.signUp(
            Register(
                email,
                password,
                passwordConfirmation,
                phoneNumber,
                name
            )
        )

    override fun checkEmail(emailRequest: EmailRequest): Observable<Boolean> =
        client.checkEmail(emailRequest)

    override fun checkPhoneNumber(phoneRequest: PhoneRequest): Observable<Boolean> =
        client.checkPhoneNumber(phoneRequest)

    override fun forgotPassword(emailRequest: EmailRequest): Observable<MessageResponse> =
        client.forgotPassword(emailRequest)

    override fun resetPasswordByVerificationCode(
        resetPasswordRequest: ResetPasswordRequest
    ): Observable<MessageResponse> = client.resetPasswordByVerificationCode(resetPasswordRequest)

    override fun activateAccount(activateRequest: ActivateRequest): Observable<MessageResponse> =
        client.activateAccount(activateRequest)

    companion object {

        @Volatile
        private var instance: SignRepositoryImpl? = null

        fun getInstance(
            sharedPrefs: SharedPrefs,
        ): SignRepositoryImpl =
            instance ?: synchronized(this) {
                instance ?: SignRepositoryImpl(sharedPrefs).also {
                    instance = it
                }
            }
    }
}
