package com.sun.qakhadelivery.extensions

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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

fun ImageView.loadUrlOrigin(url: String? = Constants.SPACE_STRING) {
    Glide.with(this)
        .asBitmap()
        .load(url)
        .placeholder(R.drawable.ic_placehoder_product)
        .override(Target.SIZE_ORIGINAL)
        .addListener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ) = false

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                val screenWidth = context.resources.displayMetrics.widthPixels
                if (resource != null && resource.width > 0 && resource.height > 0) {
                    val newHeight = (screenWidth * resource.height) / resource.width
                    val newBitmap =
                        Bitmap.createScaledBitmap(resource, screenWidth, newHeight, true)
                    setImageBitmap(newBitmap)
                }
                return true
            }
        })
        .skipMemoryCache(false)
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
