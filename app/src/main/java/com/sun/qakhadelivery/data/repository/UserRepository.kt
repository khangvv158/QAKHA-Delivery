package com.sun.qakhadelivery.data.repository

import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.data.source.remote.RetrofitClient
import com.sun.qakhadelivery.data.source.remote.UserAPI
import io.reactivex.rxjava3.core.Observable

interface UserRepository {
    fun getUser(token: String): Observable<User>
}

class UserRepositoryImpl private constructor() : UserRepository {

    private val client = RetrofitClient.getInstance().create(UserAPI::class.java)

    override fun getUser(token: String): Observable<User> = client.getUser(token)

    companion object {

        private var instance: UserRepositoryImpl? = null

        fun getInstance(): UserRepositoryImpl =
            instance ?: synchronized(this) {
                instance ?: UserRepositoryImpl().also {
                    instance = it
                }
            }
    }
}
