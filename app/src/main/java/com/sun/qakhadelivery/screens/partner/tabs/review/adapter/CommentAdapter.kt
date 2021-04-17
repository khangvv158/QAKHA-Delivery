package com.sun.qakhadelivery.screens.partner.tabs.review.adapter

import android.view.ViewGroup
import com.sun.qakhadelivery.data.model.Comment
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.CommentItem
import com.sun.qakhadelivery.widget.recyclerview.viewholder.CommentViewHolder

class CommentAdapter : CustomRecyclerView.Adapter<CommentViewHolder>(arrayListOf()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomRecyclerView.ViewHolder<*> {
        return CommentViewHolder(parent)
    }

    fun updateData(comments: MutableList<Comment>) {
        clearItems()
        addItems(comments.map {
            CommentItem(it)
        })
        notifyDataSetChanged()
    }
}
