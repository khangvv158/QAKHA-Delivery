package com.sun.qakhadelivery.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.utils.Constants

fun ImageView.loadUrl(url: String? = Constants.SPACE_STRING) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_placehoder_product)
        .into(this)
}

fun ImageView.loadAvatarUrl(url: String?) {
    Picasso.get()
        .load(url)
        .centerCrop()
        .resize(width, height)
        .error(R.drawable.ic_placehoder_product)
        .placeholder(R.drawable.ic_placehoder_product)
        .into(this)
}
