package com.sun.qakhadelivery.data.source.remote

import android.content.Context
import com.google.gson.GsonBuilder
import com.sun.qakhadelivery.data.model.TokenAccess
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsKey
import com.sun.qakhadelivery.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor(context: Context) {

    private var baseRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(
                    OkHttpClient.Builder()
                            .addInterceptor(
                                    OAuthInterceptor(SharedPrefsImpl.getInstance(context)
                                            .get(SharedPrefsKey.TOKEN_KEY, TokenAccess::class.java))
                            )
                            .build()
            )
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    fun <T> create(service: Class<T>): T {
        return baseRetrofit.create(service)
    }

    companion object {

        @Volatile
        private var instance: RetrofitClient? = null

        fun getInstance(context: Context) =
                instance ?: synchronized(this) {
                    instance ?: RetrofitClient(context).also {
                        instance = it
                    }
                }
    }
}
