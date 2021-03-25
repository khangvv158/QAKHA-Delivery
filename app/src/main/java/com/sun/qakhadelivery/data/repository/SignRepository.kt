package com.sun.qakhadelivery.data.repository

import com.sun.qakhadelivery.data.model.TokenAccess
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefs
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsKey
import com.sun.qakhadelivery.data.source.remote.RetrofitClient
import com.sun.qakhadelivery.data.source.remote.SignAPI
import com.sun.qakhadelivery.data.source.remote.schema.request.Credential
import com.sun.qakhadelivery.data.source.remote.schema.request.Register
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
}

class SignRepositoryImpl private constructor(
        private val sharedPrefs: SharedPrefs
) : SignRepository {

    private val client = RetrofitClient.getInstance().create(SignAPI::class.java)

    override fun signIn(email: String,
                        password: String
    ): Observable<TokenAccess> =
            client.signIn(
                    Credential(
                            email,
                            password
                    )
            ).doOnNext {
                sharedPrefs.put(SharedPrefsKey.TOKEN_KEY, it)
            }

    override fun signUp(
            email: String,
            password: String,
            passwordConfirmation: String,
            phoneNumber: String,
            name: String): Observable<Boolean> =
            client.signUp(
                    Register(
                            email,
                            password,
                            passwordConfirmation,
                            phoneNumber,
                            name
                    )
            )

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
