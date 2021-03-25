package com.sun.qakhadelivery.data.repository

import android.content.Context
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
        context: Context,
        private val sharedPrefs: SharedPrefs
) : UserRepository {

    private val client = RetrofitClient.getInstance(context).create(UserAPI::class.java)

    override fun getUser(): Observable<User> = client.getUser()

    override fun signOut() {
        sharedPrefs.clearKey(SharedPrefsKey.TOKEN_KEY)
    }

    companion object {

        private var instance: UserRepositoryImpl? = null

        fun getInstance(context: Context, sharedPrefs: SharedPrefs): UserRepositoryImpl =
                instance ?: synchronized(this) {
                    instance ?: UserRepositoryImpl(context, sharedPrefs).also {
                        instance = it
                    }
                }
    }
}
