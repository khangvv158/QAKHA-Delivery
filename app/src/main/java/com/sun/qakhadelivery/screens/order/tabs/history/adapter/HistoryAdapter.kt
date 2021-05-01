package com.sun.qakhadelivery.screens.order.tabs.history.adapter

import android.view.ViewGroup
import com.sun.qakhadelivery.data.source.remote.schema.response.HistoryResponse
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.HistoryItem
import com.sun.qakhadelivery.widget.recyclerview.viewholder.HistoryViewHolder

open class HistoryAdapter : CustomRecyclerView.Adapter<HistoryViewHolder>(arrayListOf()) {

    private var onClickItem: ((HistoryResponse) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomRecyclerView.ViewHolder<*> {
        return HistoryViewHolder(parent).apply {
            registerOnClickItem {
                onClickItem?.let { func ->
                    getItemPosition<HistoryItem>(it)?.let { item ->
                        func(item.history)
                    }
                }
            }
        }
    }

    open fun updateData(history: MutableList<HistoryResponse>) {
        updateItems(history.map {
            HistoryItem(it)
        })
    }

    fun setOnItemClick(onClickItem: (HistoryResponse) -> Unit) {
        this.onClickItem = onClickItem
    }
}
