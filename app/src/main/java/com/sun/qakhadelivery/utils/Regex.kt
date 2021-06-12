package com.sun.qakhadelivery.utils

object Regex {

    const val VALID_EMAIL_REGEX =
        "^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$"
    const val VALID_PHONE_REGEX = "(0[3|5|7|8|9])+([0-9]{8})\\b"
    const val VALID_PASSWORD_REGEX =
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}\$"
}
