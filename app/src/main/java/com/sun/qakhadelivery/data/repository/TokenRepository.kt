package com.sun.qakhadelivery.data.repository

import com.sun.qakhadelivery.data.model.TokenAccess
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefs
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsKey
import com.sun.qakhadelivery.utils.Constants.DEFAULT_STRING

interface TokenRepository {

    fun getToken(): String
}

class TokenRepositoryImpl private constructor(
    private val sharedPrefs: SharedPrefs
) : TokenRepository {

    override fun getToken(): String {
        return if (sharedPrefs.get(SharedPrefsKey.TOKEN_KEY, TokenAccess::class.java) != null) {
            sharedPrefs
                .get(SharedPrefsKey.TOKEN_KEY, TokenAccess::class.java)
                .token ?: DEFAULT_STRING
        } else {
            DEFAULT_STRING
        }
    }

    companion object {

        private var instance: TokenRepositoryImpl? = null

        fun getInstance(sharedPrefs: SharedPrefs): TokenRepositoryImpl =
            instance ?: synchronized(this) {
                instance ?: TokenRepositoryImpl(sharedPrefs).also {
                    instance = it
                }
            }
    }
}
