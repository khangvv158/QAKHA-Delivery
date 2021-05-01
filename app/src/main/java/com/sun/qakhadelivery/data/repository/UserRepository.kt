package com.sun.qakhadelivery.data.repository

import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.data.source.remote.RetrofitClient
import com.sun.qakhadelivery.data.source.remote.UserAPI
import com.sun.qakhadelivery.data.source.remote.schema.request.ChangePasswordRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.ChangePasswordResponse
import io.reactivex.rxjava3.core.Observable

interface UserRepository {
    fun getUser(token: String): Observable<User>
    fun changePassword(
        changePasswordRequest: ChangePasswordRequest,
        token: String
    ): Observable<ChangePasswordResponse>
}

class UserRepositoryImpl private constructor() : UserRepository {

    private val client = RetrofitClient.getInstance().create(UserAPI::class.java)

    override fun getUser(token: String): Observable<User> = client.getUser(token)

    override fun changePassword(
        changePasswordRequest: ChangePasswordRequest,
        token: String
    ) = client.changePassword(token, changePasswordRequest)

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
