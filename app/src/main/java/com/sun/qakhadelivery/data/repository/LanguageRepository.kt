package com.sun.qakhadelivery.data.repository

import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefs
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsKey
import com.sun.qakhadelivery.utils.Constants

interface LanguageRepository {
    fun setLanguage(languageCode: String)
    fun getLanguageCode(): String
}

class LanguageRepositoryImpl private constructor(private val sharedPrefs: SharedPrefs) :
    LanguageRepository {

    override fun setLanguage(languageCode: String) {
        sharedPrefs.put(SharedPrefsKey.LANG, languageCode)
    }

    override fun getLanguageCode(): String {
        return if (sharedPrefs.get(SharedPrefsKey.LANG, String::class.java).toString()
                .isNullOrEmpty()
        ) {
            Constants.LANG_EN
        } else {
            sharedPrefs.get(SharedPrefsKey.LANG, String::class.java).toString()
        }
    }

    companion object {

        private var instance: LanguageRepositoryImpl? = null

        fun getInstance(sharedPrefs: SharedPrefs): LanguageRepositoryImpl =
            instance ?: synchronized(this) {
                instance ?: LanguageRepositoryImpl(sharedPrefs).also {
                    instance = it
                }
            }
    }
}
