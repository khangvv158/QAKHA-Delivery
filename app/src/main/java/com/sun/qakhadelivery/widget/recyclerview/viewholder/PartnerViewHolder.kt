package com.sun.qakhadelivery.widget.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.extensions.loadUrl
import com.sun.qakhadelivery.extensions.setOnSafeClickListener
import com.sun.qakhadelivery.extensions.show
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
            if (item.partner.distance != Constants.DEFAULT_FLOAT) {
                textViewKilometer.text = String.format("%s Km",item.partner.distance)
                textViewKilometer.show()
            }
            if (item.partner.avgPoint != Constants.DEFAULT_FLOAT) {
                textViewRatePartner.text = item.partner.avgPoint.toString()
                appCompatImageViewRate.show()
                textViewRatePartner.show()
            }
        }
    }

    fun registerItemViewHolderListener(listener: (Int) -> Unit) {
        itemView.setOnSafeClickListener(2000) {
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
