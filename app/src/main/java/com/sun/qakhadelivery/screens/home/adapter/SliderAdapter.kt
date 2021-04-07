package com.sun.qakhadelivery.screens.home.adapter

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.sun.qakhadelivery.R

class SliderAdapter : PagerAdapter() {

    private val sliders = mutableListOf<Drawable>()

    override fun getCount() = sliders.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ShapeableImageView(container.context).apply {
            shapeAppearanceModel = this.shapeAppearanceModel.toBuilder().setAllCornerSizes(
                    resources.getDimension(R.dimen.dp_6)
            ).build()
        }
        Glide.with(container.context).load(sliders[position]).centerCrop().into(imageView)
        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun updateSlider(sliders: MutableList<Drawable>) {
        sliders.let {
            this.sliders.clear()
            this.sliders.addAll(it)
            notifyDataSetChanged()
        }
    }
}
