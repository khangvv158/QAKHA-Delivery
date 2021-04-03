package com.sun.qakhadelivery.widget.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.utils.loadUrl
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.PartnerItem
import kotlinx.android.synthetic.main.item_layout_partner.view.*

class PartnerViewHolder(viewGroup: ViewGroup)
    : CustomRecyclerView.ViewHolder<PartnerItem>(newInstance(viewGroup)) {

    override fun bind(item: PartnerItem) {
        with(itemView) {
            imageViewPartner.loadUrl("https://i.pinimg.com/564x/e3/d0/1b/e3d01be8b77d951b0ba79ea8e3cdc84e.jpg")
            textViewNamePartner?.text = item.partner.name
            textViewAddressPartner?.text = item.partner.address
            textViewRatePartner?.text = item.partner.rate.toString()
        }
    }

    fun registerItemViewHolderListener(listener: (Int) -> Unit) {
        itemView.setOnClickListener {
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
