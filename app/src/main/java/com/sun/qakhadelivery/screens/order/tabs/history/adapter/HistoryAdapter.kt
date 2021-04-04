package com.sun.qakhadelivery.screens.order.tabs.history.adapter

import android.util.Log
import android.view.ViewGroup
import com.sun.qakhadelivery.data.model.Event
import com.sun.qakhadelivery.data.model.Order
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.HistoryItem
import com.sun.qakhadelivery.widget.recyclerview.viewholder.HistoryViewHolder
import org.greenrobot.eventbus.EventBus

class HistoryAdapter : CustomRecyclerView.Adapter<HistoryViewHolder>(arrayListOf()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomRecyclerView.ViewHolder<*> {
        return HistoryViewHolder(parent).apply {
            registerOnClickItem {
                it.setOnClickListener {
                    EventBus.getDefault().post(
                            Event(EVENT_HISTORY, getItemPosition<HistoryItem>(adapterPosition)?.order)
                    )
                }
            }
        }
    }

    fun updateData(orders: MutableList<Order>) {
        mItems.clear()
        addItems(orders.map {
            Log.e("data", it.id.toString())
            HistoryItem(it)
        })
        notifyDataSetChanged()
    }

    companion object {
        const val EVENT_HISTORY = "EVENT_HISTORY"
    }
}
