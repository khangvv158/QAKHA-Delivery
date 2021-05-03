package com.sun.qakhadelivery.data.repository

import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.data.source.remote.RetrofitClient
import com.sun.qakhadelivery.data.source.remote.UserAPI
import com.sun.qakhadelivery.data.source.remote.schema.request.*
import com.sun.qakhadelivery.data.source.remote.schema.response.ChangePasswordResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.MessageResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.VerifyEmail
import io.reactivex.rxjava3.core.Observable

interface UserRepository {

    fun getUser(token: String): Observable<User>

    fun changePassword(
        changePasswordRequest: ChangePasswordRequest,
        token: String
    ): Observable<ChangePasswordResponse>

    fun updateImage(updateImage: UpdateImage, token: String): Observable<User>

    fun updateUsername(updateUsername: UpdateUsername, token: String): Observable<User>

    fun updatePhoneNumber(updatePhone: UpdatePhone, token: String): Observable<User>

    fun updateEmail(updateEmail: UpdateEmail, token: String): Observable<User>

    fun verifyEmail(verifyEmail: VerifyEmail, token: String): Observable<MessageResponse>
}

class UserRepositoryImpl private constructor() : UserRepository {

    private val client = RetrofitClient.getInstance().create(UserAPI::class.java)

    override fun getUser(token: String): Observable<User> = client.getUser(token)

    override fun changePassword(
        changePasswordRequest: ChangePasswordRequest,
        token: String
    ) = client.changePassword(token, changePasswordRequest)

    override fun updateImage(updateImage: UpdateImage, token: String): Observable<User> {
        return client.updateImage(updateImage, token)
    }

    override fun updateUsername(updateUsername: UpdateUsername, token: String): Observable<User> {
        return client.updateUsername(updateUsername, token)
    }

    override fun updatePhoneNumber(updatePhone: UpdatePhone, token: String): Observable<User> {
        return client.updatePhoneNumber(updatePhone, token)
    }

    override fun updateEmail(updateEmail: UpdateEmail, token: String): Observable<User> {
        return client.updateEmail(updateEmail, token)
    }

    override fun verifyEmail(verifyEmail: VerifyEmail, token: String): Observable<MessageResponse> {
        return client.verifyEmail(verifyEmail, token)
    }

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
