package com.sun.qakhadelivery.widget.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.TypePartnerItem
import kotlinx.android.synthetic.main.item_layout_type_partner.view.*

class TypePartnerSelectedViewHolder(viewGroup: ViewGroup) :
    CustomRecyclerView.ViewHolder<TypePartnerItem>(newInstance(viewGroup)) {

    override fun bind(item: TypePartnerItem) {
        itemView.textViewTypePartner.text = item.typePartner.name
    }

    fun registerItemViewHolderListener(listener: (Int) -> Unit) {
        itemView.setOnClickListener {
            listener(adapterPosition)
        }
    }

    companion object {

        fun newInstance(viewGroup: ViewGroup): View {
            return LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_layout_tye_partner_selected, viewGroup, false)
        }
    }
}
