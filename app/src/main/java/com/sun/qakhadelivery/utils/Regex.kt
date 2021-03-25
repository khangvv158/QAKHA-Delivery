package com.sun.qakhadelivery.utils

object Regex {

    val VALID_EMAIL_REGEX =
            "^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$".toRegex()
    val VALID_PHONE_REGEX =
            "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}".toRegex()
    val VALID_PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}\$".toRegex()
}
