package com.sun.qakhadelivery.screens.orderdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.OrderDetailMerge
import com.sun.qakhadelivery.data.source.remote.schema.response.HistoryResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.RateDriver
import com.sun.qakhadelivery.extensions.*
import com.sun.qakhadelivery.screens.feedback.driver.DriverFeedbackFragment
import com.sun.qakhadelivery.screens.feedback.partner.PartnerFeedbackFragment
import com.sun.qakhadelivery.screens.orderdetail.adapter.OrderAdapter
import com.sun.qakhadelivery.widget.recyclerview.divider.DividerItemDecorator
import kotlinx.android.synthetic.main.fragment_order_detail.*

class OrderDetailFragment : Fragment(), OrderDetailContract.View {

    private val orderAdapter by lazy { OrderAdapter() }
    private val presenter by lazy { OrderDetailPresenter() }

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
        arguments?.getParcelable<OrderDetailMerge>(BUNDLE_ORDER_DETAIL)?.let {
            namePartnerTextView.text = it.historyResponse.partner.name
            namePartnerHighTextView.text = it.historyResponse.partner.name
            totalTextView.text = it.orderDetailsResponse.order.total.toString().currencyVn()
            paymentTextView.text = it.orderDetailsResponse.order.type_checkout
            usernameTextView.text = it.orderDetailsResponse.order.name
            phoneNumberTextView.text = it.orderDetailsResponse.order.phone_number
            addressTextView.text = it.orderDetailsResponse.order.address
            statusOrderTextView.text = it.orderDetailsResponse.order.status
            priceSubtotaTtextView.text = it.orderDetailsResponse.order.subtotal.toString()
                .currencyVn()
            shippingFeeTextView.text = it.orderDetailsResponse.order.shipping_fee.toString()
                .currencyVn()
            priceDiscountTextView.text = it.orderDetailsResponse.order.discount.toString()
                .discountCurrencyVn()
            priceTotalTextView.text = it.orderDetailsResponse.order.total.toString().currencyVn()
            orderAdapter.updateOrderDetails(it.orderDetailsResponse.orderDetails)
            checkFeedback(it.RateDriver, it.historyResponse)
            //driver
            nameDriverTextView.text = it.historyResponse.driver.name
            deliveryTimeTextView.text = it.orderDetailsResponse.order.deliveryTime
            avatarCircleImageView.loadUrl(it.historyResponse.driver.image.imageUrl)
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    private fun checkFeedback(rateDriver: RateDriver, history: HistoryResponse) {
        var isPartner = false
        if (history.rate() && rateDriver.rate) {
            buttonFeedback.show()
            isPartner = true
        } else if (history.rate() && !rateDriver.rate) {
            buttonFeedback.show()
        }
        buttonFeedback.setOnSafeClickListener {
            parentFragmentManager.popBackStack()
            if (isPartner) {
                addFragmentFadeAnim(
                    PartnerFeedbackFragment.newInstance(
                        history.id,
                        history.partner.apply { id = history.partnerId }),
                    R.id.containerView
                )
            } else {
                addFragmentFadeAnim(
                    DriverFeedbackFragment.newInstance(
                        history.driver.apply { id = history.driverId },
                        history.id,
                        history.partner.apply { id = history.partnerId }
                    ), R.id.containerView
                )
            }
        }
    }

    private fun initView() {
        recyclerViewBucket.run {
            adapter = orderAdapter
            ContextCompat.getDrawable(context, R.drawable.divider)?.let {
                addItemDecoration(DividerItemDecorator(it))
            }
        }
    }

    private fun handleEvents() {
        imageViewBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        private const val BUNDLE_ORDER_DETAIL = "BUNDLE_ORDER_DETAIL"

        fun newInstance(orderDetailMerge: OrderDetailMerge) = OrderDetailFragment().apply {
            arguments = bundleOf(BUNDLE_ORDER_DETAIL to orderDetailMerge)
        }
    }
}
