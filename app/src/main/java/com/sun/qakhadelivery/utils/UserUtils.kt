package com.sun.qakhadelivery.utils

import android.content.Context
import com.sun.qakhadelivery.data.model.TokenAccess
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsKey

object UserUtils {

    fun workWithSignIn(context: Context, signIn: () -> Unit, notSignIn: () -> Unit) {
        val token = SharedPrefsImpl.getInstance(context)
            .get(SharedPrefsKey.TOKEN_KEY, TokenAccess::class.java)
        if (token == null) notSignIn() else signIn()
    }

    fun workWithSignIn(context: Context, signIn: () -> Unit) {
        val token = SharedPrefsImpl.getInstance(context)
            .get(SharedPrefsKey.TOKEN_KEY, TokenAccess::class.java)
        if (token != null) signIn()
    }
}
