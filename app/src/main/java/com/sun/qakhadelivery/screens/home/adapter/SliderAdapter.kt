package com.sun.qakhadelivery.screens.home.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.bumptech.glide.Glide
import com.sun.qakhadelivery.R
import kotlinx.android.synthetic.main.item_layout_slider.view.*

class SliderAdapter(
    context: Context,
    private val list: MutableList<Drawable>,
    isInfinite: Boolean
) :
    LoopingPagerAdapter<Drawable>(context, list, isInfinite) {

    override fun inflateView(viewType: Int, container: ViewGroup, listPosition: Int): View {
        return LayoutInflater.from(context)
            .inflate(R.layout.item_layout_slider, container, false)
    }

    override fun bindView(convertView: View, listPosition: Int, viewType: Int) {
        Glide.with(context).load(list[listPosition]).centerCrop().into(convertView.imageViewSlider)
    }
}
