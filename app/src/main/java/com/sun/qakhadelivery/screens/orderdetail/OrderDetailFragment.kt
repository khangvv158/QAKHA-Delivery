package com.sun.qakhadelivery.screens.orderdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.repository.FeedbackRepositoryImpl
import com.sun.qakhadelivery.data.repository.HistoryRepositoryImpl
import com.sun.qakhadelivery.data.repository.TokenRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.data.source.remote.schema.response.HistoryResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.OrderDetailsResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.RateDriver
import com.sun.qakhadelivery.extensions.*
import com.sun.qakhadelivery.screens.feedback.driver.DriverFeedbackFragment
import com.sun.qakhadelivery.screens.feedback.partner.PartnerFeedbackFragment
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
            ),
            FeedbackRepositoryImpl.getInstance()
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
            namePartnerTextView.text = it.partner.name
            namePartnerHighTextView.text = it.partner.name
            presenter.checkDriverFeedback(it.id)
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onSuccessOrderDetails(orderDetailsResponse: OrderDetailsResponse) {
        orderDetailsResponse.run {
            totalTextView.text = order.total.toString().currencyVn()
            paymentTextView.text = order.type_checkout
            usernameTextView.text = order.name
            phoneNumberTextView.text = order.phone_number
            addressTextView.text = order.address
            statusOrderTextView.text = order.status
            priceSubtotaTtextView.text = order.subtotal.toString().currencyVn()
            shippingFeeTextView.text = order.shipping_fee.toString().currencyVn()
            priceDiscountTextView.text = order.discount.toString().discountCurrencyVn()
            priceTotalTextView.text = order.total.toString().currencyVn()
            adapter.updateOrderDetails(orderDetails)
        }
    }

    override fun onSuccessCheckDriverFeedback(rateDriver: RateDriver) {
        var isPartner = false
        arguments?.getParcelable<HistoryResponse>(BUNDLE_HISTORY)?.let { history ->
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
    }

    override fun onErrorOrderDetails(exception: String) = Unit

    override fun onErrorCheckDriverFeedback(exception: String) {
        makeText(exception)
    }

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
