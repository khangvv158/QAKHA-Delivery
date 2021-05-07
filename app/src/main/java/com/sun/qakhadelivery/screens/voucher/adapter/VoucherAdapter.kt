package com.sun.qakhadelivery.screens.voucher.adapter

import android.view.ViewGroup
import com.sun.qakhadelivery.data.model.Voucher
import com.sun.qakhadelivery.data.source.remote.schema.response.DistanceResponse
import com.sun.qakhadelivery.utils.Constants.NOT_EXISTS
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import com.sun.qakhadelivery.widget.recyclerview.item.VoucherItem
import com.sun.qakhadelivery.widget.recyclerview.viewholder.VoucherViewHolder

class VoucherAdapter : CustomRecyclerView.Adapter<VoucherViewHolder>(arrayListOf()) {

    private var positionOld = NOT_EXISTS
    private var onSelected: ((VoucherItem) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomRecyclerView.ViewHolder<*> {
        return VoucherViewHolder(parent).apply {
            setOnClickVoucher(getItems<VoucherItem>().toMutableList()) { index, item ->
                if (positionOld != NOT_EXISTS) notifyItemChanged(positionOld)
                notifyItemChanged(index)
                positionOld = index
                onSelected?.let { it(item) }
            }
        }
    }

    fun updateDataWithCondition(
        vouchers: MutableList<Voucher>,
        total: Float,
        distance: DistanceResponse
    ) {
        clearItems()
        vouchers.map {
            VoucherItem(it).apply {
                if (voucher.condition == null) {
                    if (voucher.distanceCondition != null && distance.distance <= voucher.distanceCondition) {
                        condition = true
                    }
                } else {
                    if (voucher.distanceCondition != null
                        && distance.distance <= voucher.distanceCondition
                        && total >= voucher.condition
                    ) {
                        condition = true
                    } else if (total >= voucher.condition) condition = true
                }
            }
        }.also {
            addItems(it)
        }
    }

    fun setOnSelectionListener(onSelected: ((VoucherItem) -> Unit)?) {
        this.onSelected = onSelected
    }

    fun selectedVoucher(voucher: VoucherItem) {
        getItems<VoucherItem>().find { it.voucher == voucher.voucher }?.also {
            val index = getItemPosition(it)
            mItems[index] = voucher
            positionOld = index
            notifyItemChanged(index)
        }
    }
}
