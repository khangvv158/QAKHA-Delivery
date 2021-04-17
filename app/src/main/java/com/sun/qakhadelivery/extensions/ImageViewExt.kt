package com.sun.qakhadelivery.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sun.qakhadelivery.R

fun ImageView.loadUrl(url: String) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_placehoder_product)
        .into(this)
}
