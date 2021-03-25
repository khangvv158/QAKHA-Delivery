package com.sun.qakhadelivery.data.repository

import com.sun.qakhadelivery.data.model.TokenAccess
import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefs
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsKey
import com.sun.qakhadelivery.data.source.remote.RetrofitClient
import com.sun.qakhadelivery.data.source.remote.UserAPI
import io.reactivex.rxjava3.core.Observable

interface UserRepository {
    fun getUser(): Observable<User>
    fun signOut()
}

class UserRepositoryImpl private constructor(
        private val sharedPrefs: SharedPrefs
) : UserRepository {

    private val client = RetrofitClient.getInstance().create(UserAPI::class.java)

    override fun getUser(): Observable<User> = client.getUser(sharedPrefs.get(SharedPrefsKey.TOKEN_KEY, TokenAccess::class.java).token)

    override fun signOut() {
        sharedPrefs.clearKey(SharedPrefsKey.TOKEN_KEY)
    }

    companion object {

        private var instance: UserRepositoryImpl? = null

        fun getInstance(sharedPrefs: SharedPrefs): UserRepositoryImpl =
                instance ?: synchronized(this) {
                    instance ?: UserRepositoryImpl(sharedPrefs).also {
                        instance = it
                    }
                }
    }
}
