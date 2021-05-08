package com.sun.qakhadelivery.widget.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.extensions.loadUrl
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.CommentItem
import kotlinx.android.synthetic.main.item_layout_comment.view.*

class CommentViewHolder(viewGroup: ViewGroup) :
    CustomRecyclerView.ViewHolder<CommentItem>(newInstance(viewGroup)) {

    override fun bind(item: CommentItem) {
        with(itemView) {
            avatarImageView.loadUrl(item.comment.user.image?.imageUrl)
            nameTextView?.text = item.comment.user.name
            contentTextView.text = item.comment.content
            ratingBar.rating = item.comment.point.toFloat()
            timeTextView.text = item.comment.timeCreate
        }
    }

    companion object {
        fun newInstance(viewGroup: ViewGroup): View {
            return LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_layout_comment, viewGroup, false)
        }
    }
}
