package com.sun.qakhadelivery.screens.checkout

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.data.model.Voucher
import com.sun.qakhadelivery.data.repository.CartRepositoryImpl
import com.sun.qakhadelivery.data.repository.OrderRepositoryImpl
import com.sun.qakhadelivery.data.repository.TokenRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.data.source.remote.schema.request.ApplyVoucher
import com.sun.qakhadelivery.data.source.remote.schema.request.VoucherCancel
import com.sun.qakhadelivery.data.source.remote.schema.response.ApplyVoucherResponse
import com.sun.qakhadelivery.data.source.remote.schema.response.CancelVoucherResponse
import com.sun.qakhadelivery.extensions.addFragmentBackStack
import com.sun.qakhadelivery.extensions.gone
import com.sun.qakhadelivery.extensions.isVisible
import com.sun.qakhadelivery.extensions.show
import com.sun.qakhadelivery.screens.orderdetail.adapter.BucketAdapter
import com.sun.qakhadelivery.screens.voucher.VoucherFragment
import com.sun.qakhadelivery.utils.Constants.DEFAULT_STRING
import com.sun.qakhadelivery.widget.recyclerview.item.ChoiceVoucherState
import com.sun.qakhadelivery.widget.recyclerview.item.VoucherItem
import kotlinx.android.synthetic.main.fragment_checkout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class CheckoutFragment : Fragment(), CheckoutContract.View {

    private val adapter: BucketAdapter by lazy {
        BucketAdapter()
    }
    private val presenter: CheckoutPresenter by lazy {
        CheckoutPresenter(
            CartRepositoryImpl.getInstance(),
            OrderRepositoryImpl.getInstance(),
            TokenRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
        handleEvents()
    }

    override fun onStart() {
        super.onStart()
        presenter.run {
            setView(this@CheckoutFragment)
            arguments?.getParcelable<Partner>(BUNDLE_PARTNER)?.let {
                presenter.getVouchers(it.id)
            }
        }
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onSuccessGetCart(carts: MutableList<Cart>) {
        adapter.updateData(carts)
    }

    override fun onSuccessApplyVoucher(applyVoucherResponse: ApplyVoucherResponse) {
        voucherEditText.setText(applyVoucherResponse.voucher.code)
        textViewPriceDiscount.text = applyVoucherResponse.voucher.discount.toString()
        enableInteraction()
    }

    override fun onSuccessCancelVouchers(cancelVoucherResponse: CancelVoucherResponse) {
        textViewPriceDiscount.text = DEFAULT_STRING
        voucherEditText.setText(DEFAULT_STRING)
        arguments?.putParcelable(VOUCHER_BUNDLE, null)
        enableInteraction()
    }

    override fun onUpdateTotalPrice(total: Float) {
        textViewPriceSubtotal.text = total.toString()
        arguments?.putFloat(TOTAL_BUNDLE, total)
    }

    override fun onErrorGetVouchers(exception: String) {
        enableInteraction()
    }

    override fun onErrorGetCart(exception: String) {
        enableInteraction()
    }

    override fun onErrorApplyVoucher(exception: String) {
        arguments?.putParcelable(VOUCHER_BUNDLE, null)
        enableInteraction()
    }

    override fun onErrorCancelVouchers(exception: String) {
        enableInteraction()
    }

    override fun onSuccessGetVouchers(vouchers: MutableList<Voucher>) {
        arguments?.putParcelableArrayList(VOUCHERS_BUNDLE, ArrayList<Parcelable>(vouchers))
        enableInteraction()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageVoucher(voucherItem: VoucherItem) {
        if (voucherItem.state == ChoiceVoucherState.SELECT) {
            arguments?.run {
                getParcelable<Partner>(BUNDLE_PARTNER)?.let {
                    presenter.applyVoucher(ApplyVoucher(voucherItem.voucher.code, it.id))
                    disableInteraction()
                }
                putParcelable(VOUCHER_BUNDLE, voucherItem)
            }
        } else {
            arguments?.run {
                getParcelable<Partner>(BUNDLE_PARTNER)?.let {
                    presenter.cancelVoucher(VoucherCancel(voucherItem.voucher.id, it.id))
                    disableInteraction()
                }
            }
        }
    }

    private fun initViews() {
        recyclerViewBucket.adapter = adapter
    }

    private fun initData() {
        arguments?.run {
            putParcelable(VOUCHER_BUNDLE, null)
            getParcelable<Partner>(BUNDLE_PARTNER)?.let { partner ->
                presenter.run {
                    setView(this@CheckoutFragment)
                    getCart(partner.id, partner.getProducts())
                }
            }
        }
    }

    private fun handleEvents() {
        imageViewBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        voucherEditText.setOnClickListener {
            addFragmentBackStack(VoucherFragment.newInstance(arguments), R.id.containerView)
        }
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (loadingProgress.isVisible()) {
                        enableInteraction()
                    } else {
                        remove()
                        activity?.onBackPressed()
                    }
                }
            })
    }

    private fun disableInteraction() {
        loadingProgress.show()
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    private fun enableInteraction() {
        loadingProgress.gone()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    companion object {
        const val BUNDLE_PARTNER = "BUNDLE_PARTNER"
        const val VOUCHER_BUNDLE = "VOUCHER_BUNDLE"
        const val VOUCHERS_BUNDLE = "VOUCHERS_BUNDLE"
        const val TOTAL_BUNDLE = "TOTAL_BUNDLE"

        fun newInstance(bundle: Bundle?) = CheckoutFragment().apply {
            arguments = bundle
        }
    }
}
