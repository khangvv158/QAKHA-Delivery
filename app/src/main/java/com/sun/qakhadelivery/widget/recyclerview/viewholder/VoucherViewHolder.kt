package com.sun.qakhadelivery.widget.recyclerview.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.extensions.gone
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.ChoiceVoucherState
import com.sun.qakhadelivery.widget.recyclerview.item.VoucherItem
import kotlinx.android.synthetic.main.item_layout_voucher.view.*

class VoucherViewHolder(viewGroup: ViewGroup) :
    CustomRecyclerView.ViewHolder<VoucherItem>(newInstance(viewGroup)) {

    override fun bind(item: VoucherItem) {
        with(itemView) {
            descriptionVoucherTextView.text = item.voucher.description
            expiryDateTextView.text = item.voucher.expiryDate.toString()
            if (!item.condition) itemView.voucherRadioButton.gone()
            voucherRadioButton.isChecked = item.state != ChoiceVoucherState.NORMAL
        }
    }

    fun setOnClickVoucher(items: MutableList<VoucherItem>, update: (Int, VoucherItem) -> Unit) {
        itemView.voucherRadioButton.setOnClickListener {
            items.forEachIndexed { index, item ->
                if (item.voucher.id == items[adapterPosition].voucher.id) {
                    item.state = item.state.getNextSelectableState()
                    update(index, item)
                } else if (item.state != ChoiceVoucherState.NORMAL) {
                    item.state = ChoiceVoucherState.NORMAL
                }
            }
        }
    }

    companion object {

        fun newInstance(viewGroup: ViewGroup): View {
            return LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_layout_voucher, viewGroup, false)
        }
    }
}
