package com.sun.qakhadelivery.data.repository

import com.sun.qakhadelivery.data.model.TokenAccess
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefs
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsKey
import com.sun.qakhadelivery.data.source.remote.RetrofitClient
import com.sun.qakhadelivery.data.source.remote.SignAPI
import com.sun.qakhadelivery.data.source.remote.schema.request.Credential
import com.sun.qakhadelivery.data.source.remote.schema.request.EmailRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.PhoneRequest
import com.sun.qakhadelivery.data.source.remote.schema.request.Register
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
    ): Observable<Boolean>

    fun checkEmail(emailRequest: EmailRequest): Observable<Boolean>

    fun checkPhoneNumber(phoneRequest: PhoneRequest): Observable<Boolean>
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
                if (it.role == Constants.ROLE_MEMBER) {
                    sharedPrefs.put(SharedPrefsKey.TOKEN_KEY, it)
                }
            }

    override fun signUp(
            email: String,
            password: String,
            passwordConfirmation: String,
            phoneNumber: String,
            name: String
    ): Observable<Boolean> =
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
