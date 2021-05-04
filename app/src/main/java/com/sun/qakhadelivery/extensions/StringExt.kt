package com.sun.qakhadelivery.extensions

import android.text.TextUtils
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

fun String.toInt(): Int {
    return try {
        Integer.parseInt(this)
    } catch (e: NumberFormatException) {
        Integer.MIN_VALUE
    }
}

fun String.toDouble(): Double {
    return try {
        java.lang.Double.parseDouble(this)
    } catch (e: NumberFormatException) {
        Double.MIN_VALUE
    }
}

@Throws(ParseException::class)
fun String.toDate(format: String): Date {
    val parser = SimpleDateFormat(format, Locale.getDefault())
    return parser.parse(this)
}

@Throws(ParseException::class)
fun String.toDateWithFormat(inputFormat: String, outputFormat: String): String {
    val gmtTimeZone = TimeZone.getTimeZone("UTC")
    val inputDateTimeFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
    inputDateTimeFormat.timeZone = gmtTimeZone

    val outputDateTimeFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
    outputDateTimeFormat.timeZone = gmtTimeZone
    return outputDateTimeFormat.format(inputDateTimeFormat.parse(this))
}

fun String.validWithPattern(pattern: Pattern): Boolean {
    return pattern.matcher(toLowerCase()).find()
}

fun String.validWithRegex(regex: String): Boolean {
    return Pattern.compile(regex).matcher(this).find()
}


fun String.currencyVn(): String {
    val format = DecimalFormat("#,##0.00")
    format.currency = Currency.getInstance(Locale.US)
    var price = if (!TextUtils.isEmpty(this)) this else "0"
    price = price.trim { it <= ' ' }
    price = format.format(price.toDouble())
    price = price.replace(",".toRegex(), "\\.")
    if (price.endsWith(".00")) {
        val centsIndex = price.lastIndexOf(".00")
        if (centsIndex != -1) {
            price = price.substring(0, centsIndex)
        }
    }
    return String.format("%s đ", price)
}

fun String.discountCurrencyVn(): String {
    val format = DecimalFormat("#,##0.00")
    format.currency = Currency.getInstance(Locale.US)
    var price = if (!TextUtils.isEmpty(this)) this else "0"
    price = price.trim { it <= ' ' }
    price = format.format(price.toDouble())
    price = price.replace(",".toRegex(), "\\.")
    if (price.endsWith(".00")) {
        val centsIndex = price.lastIndexOf(".00")
        if (centsIndex != -1) {
            price = price.substring(0, centsIndex)
        }
    }
    return String.format("- %s đ", price)
}
