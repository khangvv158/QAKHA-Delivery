package com.sun.qakhadelivery.screens.voucher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Voucher
import com.sun.qakhadelivery.screens.checkout.CheckoutFragment.Companion.TOTAL_BUNDLE
import com.sun.qakhadelivery.screens.checkout.CheckoutFragment.Companion.VOUCHERS_BUNDLE
import com.sun.qakhadelivery.screens.checkout.CheckoutFragment.Companion.VOUCHER_BUNDLE
import com.sun.qakhadelivery.screens.voucher.adapter.VoucherAdapter
import com.sun.qakhadelivery.widget.recyclerview.item.VoucherItem
import kotlinx.android.synthetic.main.fragment_voucher.*
import org.greenrobot.eventbus.EventBus

class VoucherFragment : Fragment() {

    private val adapter: VoucherAdapter by lazy {
        VoucherAdapter()
    }
    private var voucherCurrent: VoucherItem? = null
    private var voucherSelected: VoucherItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_voucher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        handleEvents()
    }

    private fun initViews() {
        voucherRecyclerView.adapter = adapter
        arguments?.run {
            getParcelableArrayList<Voucher>(VOUCHERS_BUNDLE)?.let {
                getFloat(TOTAL_BUNDLE).let { total ->
                    adapter.updateDataWithCondition(it, total)
                }
            }
            getParcelable<VoucherItem>(VOUCHER_BUNDLE)?.let {
                adapter.selectedVoucher(it)
                voucherSelected = it
            }
        }
    }

    private fun handleEvents() {
        imageViewBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        adapter.setOnSelectionListener {
            voucherCurrent = it
        }
        usageButton.setOnClickListener {
            if (voucherSelected != null && voucherCurrent?.isSelected() == false) {
                EventBus.getDefault().post(voucherCurrent)
                parentFragmentManager.popBackStack()
            } else if (voucherCurrent != null && voucherCurrent?.isSelected() == true) {
                EventBus.getDefault().post(voucherCurrent)
                parentFragmentManager.popBackStack()
            } else if (voucherSelected != null && voucherCurrent == null) {
                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.notification_voucher_selection),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {

        fun newInstance(bundle: Bundle?) = VoucherFragment().apply {
            arguments = bundle
        }
    }
}
