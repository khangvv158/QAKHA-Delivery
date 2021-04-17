package com.sun.qakhadelivery.widget.recyclerview.item

import android.os.Parcelable
import com.sun.qakhadelivery.data.model.Voucher
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class ChoiceVoucherState : Parcelable {
    NORMAL,
    SELECT;

    fun getNextSelectableState(): ChoiceVoucherState {
        return when (this) {
            NORMAL -> SELECT
            SELECT -> NORMAL
        }
    }
}

@Parcelize
data class VoucherItem(
    val voucher: Voucher,
    var state: ChoiceVoucherState = ChoiceVoucherState.NORMAL,
    var condition: Boolean = false
) : CustomRecyclerView.Item(), Parcelable {

    fun isSelected(): Boolean {
        return state == ChoiceVoucherState.SELECT
    }
}
