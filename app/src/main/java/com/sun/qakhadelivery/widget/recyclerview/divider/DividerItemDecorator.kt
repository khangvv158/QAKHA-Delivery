package com.sun.qakhadelivery.widget.recyclerview.divider

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecorator(private val divider: Drawable) : RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val dividerLeft: Int = parent.paddingLeft
        val dividerRight: Int = parent.width - parent.paddingRight
        val childCount: Int = parent.childCount
        for (i in 0..childCount - 2) {
            val child = parent.getChildAt(i)
            val params: RecyclerView.LayoutParams = child.layoutParams as RecyclerView.LayoutParams
            val dividerTop: Int = child.bottom + params.bottomMargin
            val dividerBottom: Int = dividerTop + divider.intrinsicHeight
            divider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            divider.draw(c)
        }
    }
}