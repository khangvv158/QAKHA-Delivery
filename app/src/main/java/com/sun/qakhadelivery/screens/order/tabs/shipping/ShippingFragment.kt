package com.sun.qakhadelivery.screens.order.tabs.shipping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Refresh
import com.sun.qakhadelivery.data.repository.ShippingRepositoryImpl
import com.sun.qakhadelivery.data.repository.TokenRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.data.source.remote.schema.response.HistoryResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.OrderResponse
import com.sun.qakhadelivery.extensions.addFragmentSlideAnim
import com.sun.qakhadelivery.screens.order.tabs.shipping.adapter.ShippingAdapter
import com.sun.qakhadelivery.screens.shippingdetail.OnOrderDone
import com.sun.qakhadelivery.screens.shippingdetail.ShippingDetailFragment
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_shipping.*
import kotlinx.android.synthetic.main.fragment_shipping.refreshLayout
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ShippingFragment : Fragment(), ShippingContract.View, OnOrderDone {

    private val shippingAdapter: ShippingAdapter by lazy {
        ShippingAdapter()
    }
    private val presenter by lazy {
        ShippingPresenter(
            TokenRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
            ),
            ShippingRepositoryImpl.getInstance()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shipping, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        handleEvent()
    }

    private fun handleEvent() {
        shippingAdapter.setOnItemClick {
            presenter.trackingOrder(it.id)
        }
        refreshLayout.setOnRefreshListener {
            presenter.getShipping()
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.run {
            setView(this@ShippingFragment)
            getShipping()
        }
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onSuccessGetShipping(shipping: MutableList<HistoryResponse>) {
        shippingAdapter.updateData(shipping)
        refreshLayout.isRefreshing = false
    }

    override fun onSuccessTrackingOrder(orderResponse: OrderResponse) {
        parentFragment?.addFragmentSlideAnim(ShippingDetailFragment.newInstance(Bundle().apply {
            putParcelable(ShippingDetailFragment.BUNDLE_ORDER_RESPONSE, orderResponse)
        }).apply {
            registerOnOrderDone(this@ShippingFragment)
        }, R.id.containerView)
    }

    override fun onErrorGetShipping(exception: String) {
        refreshLayout.isRefreshing = false
    }

    override fun onErrorTrackingOrder(exception: String) {
        Toast.makeText(
            context,
            getString(R.string.query_shipper_error),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onOrderDone() {
        presenter.getShipping()
        refreshLayout.isRefreshing = true
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onLoadRefresh(refresh: Refresh) {
        refresh.message {
            refreshLayout.isRefreshing = true
            presenter.getShipping()
        }
    }

    private fun initRecyclerView() {
        recyclerViewShipping.adapter = shippingAdapter
    }

    companion object {

        fun newInstance() = ShippingFragment()
    }
}
