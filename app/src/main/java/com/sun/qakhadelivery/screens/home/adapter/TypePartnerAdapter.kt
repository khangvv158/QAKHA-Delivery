package com.sun.qakhadelivery.screens.home.adapter

import android.view.ViewGroup
import com.sun.qakhadelivery.data.model.TypePartner
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.TypePartnerItem
import com.sun.qakhadelivery.widget.recyclerview.viewholder.TypePartnerSelectedViewHolder
import com.sun.qakhadelivery.widget.recyclerview.viewholder.TypePartnerViewHolder

class TypePartnerAdapter : CustomRecyclerView.Adapter<TypePartnerViewHolder>(arrayListOf()) {

    private var positionSelected = 0
    private var listener: TypePartnerRecyclerViewOnClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomRecyclerView.ViewHolder<*> {
        return if (viewType == ITEM_VIEW_SELECTED) {
            TypePartnerSelectedViewHolder(parent).apply {
                registerItemViewHolderListener {
                    getItemPosition<TypePartnerItem>(it)?.typePartner?.let { typePartner ->
                        listener?.onItemClickListener(typePartner, it)
                    }
                }
            }
        } else {
            TypePartnerViewHolder(parent).apply {
                registerItemViewHolderListener {
                    getItemPosition<TypePartnerItem>(it)?.typePartner?.let { typePartner ->
                        listener?.onItemClickListener(typePartner, it)
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == positionSelected) {
            ITEM_VIEW_SELECTED
        } else {
            ITEM_VIEW_NOT_SELECTED
        }
    }

    fun updateData(typePartners: MutableList<TypePartner>) {
        addItems(typePartners.map {
            TypePartnerItem(it)
        })
    }

    fun selectTypePartner(positionSelect: Int) {
        positionSelected = positionSelect
        notifyItemChanged(positionSelect)
    }

    fun getItemFirst(): TypePartner? {
        return getItemPosition<TypePartnerItem>(0)?.typePartner
    }

    fun registerRecyclerViewListener(listener: TypePartnerRecyclerViewOnClickListener) {
        this.listener = listener
    }

    companion object {

        const val ITEM_VIEW_SELECTED = 0
        const val ITEM_VIEW_NOT_SELECTED = 1
    }
}
