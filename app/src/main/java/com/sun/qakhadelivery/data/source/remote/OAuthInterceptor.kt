package com.sun.qakhadelivery.data.source.remote

import com.sun.qakhadelivery.data.model.TokenAccess
import com.sun.qakhadelivery.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class OAuthInterceptor (private val tokenAccess: TokenAccess?) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
                .newBuilder()
                .header(Constants.AUTHORIZATION, Constants.TOKEN_TYPE + tokenAccess?.token)
                .header(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_VALUE)
                .build()
        return chain.proceed(request)
    }
}
