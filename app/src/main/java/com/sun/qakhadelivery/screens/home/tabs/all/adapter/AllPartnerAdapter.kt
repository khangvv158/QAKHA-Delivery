package com.sun.qakhadelivery.screens.home.tabs.all.adapter

import android.view.ViewGroup
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.utils.OnItemRecyclerViewClickListener
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.PartnerItem
import com.sun.qakhadelivery.widget.recyclerview.viewholder.PartnerViewHolder

class AllPartnerAdapter : CustomRecyclerView.Adapter<PartnerViewHolder>(arrayListOf()) {

    private var listener: OnItemRecyclerViewClickListener<Partner>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomRecyclerView.ViewHolder<*> {
        return PartnerViewHolder(parent).apply {
            registerItemViewHolderListener {
                getItemPosition<PartnerItem>(it)?.partner?.let { partner ->
                    listener?.onItemClickListener(partner)
                }
            }
        }
    }

    fun updateData(partners: MutableList<Partner>) {
        mItems.clear()
        addItems(partners.map {
            PartnerItem(it)
        })
        notifyDataSetChanged()
    }

    fun registerRecyclerViewListener(listener: OnItemRecyclerViewClickListener<Partner>) {
        this.listener = listener
    }
}
