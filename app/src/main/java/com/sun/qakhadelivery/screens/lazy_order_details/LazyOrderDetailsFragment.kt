package com.sun.qakhadelivery.screens.lazy_order_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.OrderDetailMerge
import com.sun.qakhadelivery.data.repository.FeedbackRepositoryImpl
import com.sun.qakhadelivery.data.repository.HistoryRepositoryImpl
import com.sun.qakhadelivery.data.repository.TokenRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.data.source.remote.schema.response.HistoryResponse
import com.sun.qakhadelivery.extensions.*
import com.sun.qakhadelivery.screens.orderdetail.OrderDetailFragment
import kotlinx.android.synthetic.main.fragment_lazy_loading.*
import kotlinx.android.synthetic.main.fragment_order_detail.*

class LazyOrderDetailsFragment : Fragment(), LazyOrderDetailsContract.View {

    private val presenter by lazy {
        LazyOrderDetailsPresenter(
            HistoryRepositoryImpl.getInstance(),
            TokenRepositoryImpl.getInstance(SharedPrefsImpl.getInstance(requireContext())),
            FeedbackRepositoryImpl.getInstance()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lazy_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEvents()
    }

    override fun onStart() {
        super.onStart()
        presenter.run {
            setView(this@LazyOrderDetailsFragment)
            arguments?.getParcelable<HistoryResponse>(BUNDLE_HISTORY)?.let {
                presenter.getOrderDetail(it.id, it)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onSuccessOrderDetails(orderDetailMerge: OrderDetailMerge) {
        parentFragmentManager.popBackStack()
        addFragmentBackStack(OrderDetailFragment.newInstance(orderDetailMerge), R.id.containerView)
    }

    override fun onErrorOrderDetails(exception: String) {
        makeText(exception)
        loadingProgress.gone()
        merchantErrorTextView.run {
            text = getString(R.string.order_not_available)
            show()
        }
        descriptionErrorTextView.run {
            text = getString(R.string.please_check_order)
            show()
        }
        pendingImageView.show()
        buttonBack.show()
    }

    private fun handleEvents() {
        buttonBack.setOnSafeClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        private const val BUNDLE_HISTORY = "BUNDLE_HISTORY"

        fun newInstance(historyResponse: HistoryResponse) = LazyOrderDetailsFragment().apply {
            arguments = bundleOf(BUNDLE_HISTORY to historyResponse)
        }
    }
}
