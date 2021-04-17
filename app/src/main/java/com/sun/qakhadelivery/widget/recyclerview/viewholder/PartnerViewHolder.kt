package com.sun.qakhadelivery.widget.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.extensions.loadUrl
import com.sun.qakhadelivery.extensions.setOnSafeClickListener
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.PartnerItem
import kotlinx.android.synthetic.main.item_layout_partner.view.*

class PartnerViewHolder(viewGroup: ViewGroup) :
    CustomRecyclerView.ViewHolder<PartnerItem>(newInstance(viewGroup)) {

    override fun bind(item: PartnerItem) {
        with(itemView) {
            imageViewPartner.loadUrl(item.partner.image.imageUrl)
            textViewNamePartner?.text = item.partner.name
            textViewAddressPartner?.text = item.partner.address
            textViewRatePartner?.text = item.partner.rate.toString()
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
