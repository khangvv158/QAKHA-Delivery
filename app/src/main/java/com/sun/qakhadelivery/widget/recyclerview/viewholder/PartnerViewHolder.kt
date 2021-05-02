package com.sun.qakhadelivery.widget.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.extensions.loadUrl
import com.sun.qakhadelivery.extensions.setOnSafeClickListener
import com.sun.qakhadelivery.utils.Constants
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.PartnerItem
import kotlinx.android.synthetic.main.item_layout_partner.view.*

class PartnerViewHolder(viewGroup: ViewGroup) :
    CustomRecyclerView.ViewHolder<PartnerItem>(newInstance(viewGroup)) {

    override fun bind(item: PartnerItem) {
        with(itemView) {
            item.partner.image?.let { imageViewPartner.loadUrl(it.imageUrl) }
            textViewNamePartner?.text = item.partner.name
            textViewAddressPartner?.text = item.partner.address
            textViewRatePartner?.text = item.partner.rate.toString()
            if (item.partner.distance != Constants.DEFAULT_FLOAT) {
                textViewKilometer.text = item.partner.distance.toString()
            }
            if (item.partner.avgPoint != Constants.DEFAULT_FLOAT) {
                textViewRatePartner.text = item.partner.avgPoint.toString()
            }
        }
    }

    fun registerItemViewHolderListener(listener: (Int) -> Unit) {
        itemView.setOnSafeClickListener {
            listener(adapterPosition)
        }
    }

    companion object {
        fun newInstance(viewGroup: ViewGroup): View {
            return LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_layout_partner, viewGroup, false)
        }
    }
}
