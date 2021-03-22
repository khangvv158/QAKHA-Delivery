package com.sun.qakhadelivery.data.repository

import android.content.Context
import com.sun.qakhadelivery.data.model.TokenAccess
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefs
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsKey
import com.sun.qakhadelivery.data.source.remote.RetrofitClient
import com.sun.qakhadelivery.data.source.remote.SignAPI
import com.sun.qakhadelivery.data.source.remote.schema.request.Credential
import io.reactivex.rxjava3.core.Observable

interface SignRepository {
    fun signIn(
            email: String,
            password: String,
            passwordConfirmation: String): Observable<TokenAccess>
}

class SignRepositoryImpl private constructor(
        context: Context,
        private val sharedPrefs: SharedPrefs
) : SignRepository {

    private val client = RetrofitClient.getInstance(context).create(SignAPI::class.java)

    override fun signIn(email: String,
                        password: String,
                        passwordConfirmation: String
    ): Observable<TokenAccess> =
            client.signIn(
                    Credential(
                            email,
                            password,
                            passwordConfirmation
                    )
            ).apply {
                sharedPrefs.put(SharedPrefsKey.TOKEN_KEY, blockingSingle())
            }

    companion object {

        @Volatile
        private var instance: SignRepositoryImpl? = null

        fun getInstance(
                context: Context,
                sharedPrefs: SharedPrefs,
        ): SignRepositoryImpl =
                instance ?: synchronized(this) {
                    instance ?: SignRepositoryImpl(context, sharedPrefs).also {
                        instance = it
                    }
                }
    }
}
