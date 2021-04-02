package com.sun.qakhadelivery.screens.home.adapter

import android.view.ViewGroup
import com.sun.qakhadelivery.data.model.TypePartner
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.TypePartnerItem
import com.sun.qakhadelivery.widget.recyclerview.viewholder.TypePartnerViewHolder

class TypePartnerAdapter : CustomRecyclerView.Adapter<TypePartnerViewHolder>(arrayListOf()) {

    private var listener: TypePartnerRecyclerViewOnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomRecyclerView.ViewHolder<*> {
        return TypePartnerViewHolder(parent).apply {
            registerItemViewHolderListener {
                getItemPosition<TypePartnerItem>(it)?.typePartner?.let { typePartner ->
                    listener?.onItemClickListener(typePartner)
                }
            }
        }
    }

    fun updateData(typePartners: MutableList<TypePartner>) {
        mItems.clear()
        addItems(typePartners.map {
            TypePartnerItem(it)
        })
    }

    fun getItemFirst(): TypePartner? {
        return getItemPosition<TypePartnerItem>(0)?.typePartner
    }

    fun registerRecyclerViewListener(listener: TypePartnerRecyclerViewOnClickListener) {
        this.listener = listener
    }
}
