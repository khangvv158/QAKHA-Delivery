package com.sun.qakhadelivery.data.source.remote

import com.sun.qakhadelivery.data.model.TokenAccess
import com.sun.qakhadelivery.data.source.remote.schema.request.Credential
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface SignAPI {

    @POST("sign_in")
    fun signIn(@Body parameterName: Credential): Observable<TokenAccess>
}
