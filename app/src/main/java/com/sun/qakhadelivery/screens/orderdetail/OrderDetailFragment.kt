package com.sun.qakhadelivery.screens.orderdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.repository.HistoryRepositoryImpl
import com.sun.qakhadelivery.data.repository.TokenRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.data.source.remote.schema.response.HistoryResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.OrderDetailsResponse
import com.sun.qakhadelivery.extensions.gone
import com.sun.qakhadelivery.screens.order.tabs.history.HistoryFragment.Companion.BUNDLE_HISTORY
import com.sun.qakhadelivery.screens.orderdetail.adapter.OrderAdapter
import kotlinx.android.synthetic.main.fragment_order_detail.*

class OrderDetailFragment : Fragment(), OrderDetailContract.View {

    private val adapter by lazy { OrderAdapter() }
    private val presenter by lazy {
        OrderDetailPresenter(
            HistoryRepositoryImpl.getInstance(),
            TokenRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        handleEvents()
    }

    override fun onStart() {
        super.onStart()
        arguments?.getParcelable<HistoryResponse>(BUNDLE_HISTORY)?.let {
            presenter.run {
                setView(this@OrderDetailFragment)
                getOrderDetail(it.id)
            }
            nameDriverTextView.text = it.driver.name
            namePartnerTextView.text = it.partner.name
            namePartnerHighTextView.text = it.partner.name
            if (!it.isRated()) buttonFeedback.gone()
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onSuccessOrderDetails(orderDetailsResponse: OrderDetailsResponse) {
        orderDetailsResponse.run {
            totalTextView.text = order.total.toString()
            paymentTextView.text = order.type_checkout
            usernameTextView.text = order.name
            phoneNumberTextView.text = order.phone_number
            addressTextView.text = order.address
            statusOrderTextView.text = order.status
            orderIdTextView.text = order.id.toString()
            priceSubtotaTtextView.text = order.subtotal.toString()
            shippingFeeTextView.text = order.shipping_fee.toString()
            priceDiscountTextView.text = order.discount.toString()
            priceTotalTextView.text = order.total.toString()
            adapter.updateOrderDetails(orderDetails)
        }
    }

    override fun onErrorOrderDetails(exception: String) = Unit

    private fun initView() {
        recyclerViewBucket.adapter = adapter
    }

    private fun handleEvents() {
        imageViewBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        fun newInstance(bundle: Bundle) = OrderDetailFragment().apply {
            arguments = bundle
        }
    }
}
