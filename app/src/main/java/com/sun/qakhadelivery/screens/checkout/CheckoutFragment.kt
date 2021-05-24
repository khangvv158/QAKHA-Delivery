package com.sun.qakhadelivery.screens.checkout

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.*
import com.sun.qakhadelivery.data.repository.CartRepositoryImpl
import com.sun.qakhadelivery.data.repository.OrderRepositoryImpl
import com.sun.qakhadelivery.data.repository.TokenRepositoryImpl
import com.sun.qakhadelivery.data.repository.UserRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.data.source.remote.schema.request.*
import com.sun.qakhadelivery.data.source.remote.schema.response.*
import com.sun.qakhadelivery.extensions.*
import com.sun.qakhadelivery.screens.address.AddressFragment
import com.sun.qakhadelivery.screens.checkout.adapter.BucketAdapter
import com.sun.qakhadelivery.screens.container.ContainerFragment
import com.sun.qakhadelivery.screens.order.tabs.shipping.ShippingFragment
import com.sun.qakhadelivery.screens.shippingdetail.ShippingDetailFragment
import com.sun.qakhadelivery.screens.shippingdetail.ShippingDetailFragment.Companion.BUNDLE_ORDER_RESPONSE
import com.sun.qakhadelivery.screens.voucher.VoucherFragment
import com.sun.qakhadelivery.utils.Constants.DEFAULT_FLOAT
import com.sun.qakhadelivery.utils.Constants.DEFAULT_STRING
import com.sun.qakhadelivery.utils.Constants.NOT_EXISTS
import com.sun.qakhadelivery.utils.IPositiveNegativeListener
import com.sun.qakhadelivery.utils.LocationHelper
import com.sun.qakhadelivery.widget.recyclerview.divider.DividerItemDecorator
import com.sun.qakhadelivery.widget.recyclerview.item.ChoiceVoucherState
import com.sun.qakhadelivery.widget.recyclerview.item.VoucherItem
import com.sun.qakhadelivery.widget.view.DialogQueryShipping
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_checkout.*
import kotlinx.android.synthetic.main.fragment_checkout.loadingProgress
import kotlinx.android.synthetic.main.item_layout_voucher.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class CheckoutFragment : Fragment(), CheckoutContract.View {

    private val bucketAdapter: BucketAdapter by lazy {
        BucketAdapter()
    }
    private val presenter: CheckoutPresenter by lazy {
        CheckoutPresenter(
            CartRepositoryImpl.getInstance(),
            OrderRepositoryImpl.getInstance(),
            TokenRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
            ),
            UserRepositoryImpl.getInstance()
        )
    }
    private val dialogQueryShipping by lazy { DialogQueryShipping() }

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
                getVouchers(it.id)
            }
            getUserInfo()
        }
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onSuccessGetCart(carts: MutableList<Cart>) {
        bucketAdapter.updateData(carts)
        arguments?.run {
            getParcelable<Partner>(BUNDLE_PARTNER)?.let { partner ->
                getParcelable<Address>(BUNDLE_ADDRESS)?.let { address ->
                    addressTextView.text = address.name
                    presenter.calculatorDistance(
                        distanceRequest = DistanceRequest(
                            partner.id,
                            address.latitude,
                            address.longitude
                        )
                    )
                }
            }
        }
    }

    override fun onSuccessApplyVoucher(applyVoucherResponse: ApplyVoucherResponse) {
        voucherEditText.setText(applyVoucherResponse.voucher.code)
        priceDiscountTextView.text = applyVoucherResponse.voucher
            .discount.toString().discountCurrencyVn()
        arguments?.getParcelable<DistanceResponse>(BUNDLE_DISTANCE)?.run {
            setTotal(shipping_fee + applyVoucherResponse.totalAfterDiscount)
        }
        enableInteraction()
    }

    override fun onSuccessCancelVouchers(cancelVoucherResponse: CancelVoucherResponse) {
        clearVoucher()
        arguments?.getParcelable<DistanceResponse>(BUNDLE_DISTANCE)?.run {
            setTotal(cancelVoucherResponse.subtotal + shipping_fee)
        }
        enableInteraction()
    }

    override fun onUpdateTotalPrice(total: Float) {
        priceSubTotalTextView.text = total.toString().currencyVn()
        setTotal(total)
        arguments?.putFloat(BUNDLE_TOTAL, total)
    }

    override fun onSuccessGetVouchers(vouchers: MutableList<Voucher>) {
        arguments?.putParcelableArrayList(BUNDLE_VOUCHERS, ArrayList<Parcelable>(vouchers))
        enableInteraction()
    }

    override fun onSuccessGetUser(user: User) {
        userNameTextView.text = user.name
        phoneNumberTextView.text = user.phoneNumber
        arguments?.putParcelable(BUNDLE_USER, user)
        coinsRadioButton.text = String.format(
            getString(R.string.show_coins),
            if (user.coin == DEFAULT_FLOAT) getString(R.string.zero)
            else user.coin
        )
        enableInteraction()
    }

    override fun onSuccessDistance(distanceResponse: DistanceResponse) {
        if (!distanceResponse.isMaxDistance()) {
            shippingFeeTextView.text = distanceResponse.shipping_fee.toString().currencyVn()
            distanceTextView.text = String.format(
                getString(R.string.distance_currency_checkout),
                distanceResponse.distance
            )
            arguments?.run {
                val totalDistance = distanceResponse.shipping_fee + getFloat(BUNDLE_TOTAL)
                setTotal(totalDistance)
                putParcelable(BUNDLE_DISTANCE, distanceResponse)
            }
        } else {
            addressTextView.text = getString(R.string.choose_address)
            arguments?.putParcelable(BUNDLE_ADDRESS, null)
            context?.showDialogWithListener(
                getString(R.string.notification),
                getString(R.string.notification_more_than_distance),
                object : IPositiveNegativeListener {

                    override fun onPositive() {
                        addFragmentBackStack(AddressFragment.newInstance(), R.id.containerView)
                    }
                }, getString(R.string.answer_ok), true
            )
        }
        enableInteraction()
    }

    override fun onSuccessCreateOrder(orderResponse: OrderResponse) {
        dialogQueryShipping.dismiss()
        parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        addFragmentSlideAnim(ShippingDetailFragment.newInstance(Bundle().apply {
            putParcelable(BUNDLE_ORDER_RESPONSE, orderResponse)
        }), R.id.containerView)
        EventBus.getDefault().run {
            post(Refresh(this::class.java, ShippingFragment::class.java))
            post(Refresh(this::class.java, ContainerFragment::class.java))
        }
    }

    override fun onSuccessCreateOrderWithVoucher(orderResponse: OrderResponse) {
        dialogQueryShipping.dismiss()
        parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        addFragmentSlideAnim(ShippingDetailFragment.newInstance(Bundle().apply {
            putParcelable(BUNDLE_ORDER_RESPONSE, orderResponse)
        }), R.id.containerView)
        EventBus.getDefault().run {
            post(Refresh(this::class.java, ShippingFragment::class.java))
            post(Refresh(this::class.java, ContainerFragment::class.java))
        }
    }

    override fun onErrorGetUser(exception: String) {
        enableInteraction()
        makeText(exception)
    }

    override fun onErrorGetVouchers(exception: String) {
        enableInteraction()
        makeText(exception)
    }

    override fun onErrorGetCart(exception: String) {
        enableInteraction()
        makeText(exception)
    }

    override fun onErrorApplyVoucher(exception: String) {
        arguments?.putParcelable(BUNDLE_VOUCHER, null)
        enableInteraction()
        makeText(exception)
    }

    override fun onErrorCancelVouchers(exception: String) {
        arguments?.getParcelable<VoucherItem>(BUNDLE_VOUCHER)?.let {
            it.state = ChoiceVoucherState.SELECT
        }
        enableInteraction()
        makeText(exception)
    }

    override fun onErrorCalculatorDistance(exception: String) {
        enableInteraction()
        makeText(exception)
    }

    override fun onErrorCreateOrder(exception: String) {
        makeText(exception)
        dialogQueryShipping.dismiss()
    }

    override fun onErrorCreateOrderWithVoucher(exception: String) {
        makeText(exception)
        dialogQueryShipping.dismiss()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageVoucher(voucherItem: VoucherItem) {
        if (voucherItem.state == ChoiceVoucherState.SELECT) {
            arguments?.run {
                getParcelable<Partner>(BUNDLE_PARTNER)?.let { partner ->
                    getParcelable<DistanceResponse>(BUNDLE_DISTANCE)?.let { distance ->
                        if (voucherItem.voucher.distanceCondition == null) {
                            presenter.applyVoucherTotal(
                                VoucherTotal(
                                    voucherItem.voucher.code,
                                    partner.id
                                )
                            )
                        } else {
                            presenter.applyVoucherDistance(
                                VoucherDistance(
                                    voucherItem.voucher.code,
                                    partner.id,
                                    distance.distance
                                )
                            )
                        }
                        disableInteraction()
                    }
                }
                putParcelable(BUNDLE_VOUCHER, voucherItem)
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageAddress(address: Address) {
        addressTextView.text = address.name
        clearVoucher()
        arguments?.run {
            getParcelable<Partner>(BUNDLE_PARTNER)?.let {
                putParcelable(BUNDLE_ADDRESS, address)
                presenter.calculatorDistance(
                    DistanceRequest(
                        it.id,
                        address.latitude,
                        address.longitude
                    )
                )
                presenter.getVouchers(it.id)
            }
        }
    }

    private fun clearVoucher() {
        arguments?.run {
            voucherEditText.text?.clear()
            priceDiscountTextView.text = DEFAULT_STRING
            putParcelable(BUNDLE_VOUCHER, null)
        }
    }

    private fun initViews() {
        recyclerViewBucket.run {
            adapter = bucketAdapter
            ContextCompat.getDrawable(context, R.drawable.divider)?.let {
                addItemDecoration(DividerItemDecorator(it))
            }
        }
    }

    private fun initData() {
        arguments?.run {
            putParcelable(BUNDLE_VOUCHER, null)
            getParcelable<Partner>(BUNDLE_PARTNER)?.let { partner ->
                namePartnerHighTextView.text = partner.name
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
            arguments?.getParcelable<Address>(BUNDLE_ADDRESS).let {
                if (it != null) {
                    addFragmentBackStack(VoucherFragment.newInstance(arguments), R.id.containerView)
                } else {
                    makeText(getString(R.string.notification_choose_address))
                }
            }
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
        addressLayout.setOnSafeClickListener {
            addFragmentBackStack(AddressFragment.newInstance(), R.id.containerView)
        }
        arguments?.getParcelable<Address>(BUNDLE_ADDRESS).also {
            if (it == null) {
                showDialogRequireAddress()
            }
        }
        var idChecked = NOT_EXISTS
        paymentGroupRadio.setOnCheckedChangeListener { _, checkedId ->
            idChecked = checkedId
        }
        placeOrderCardView.setOnSafeClickListener {
            when (idChecked) {
                R.id.cashRadioButton -> {
                    createOrderWithTypeCheckout(TypeCheckout.CASH.value)
                }
                R.id.coinsRadioButton -> {
                    createOrderWithTypeCheckout(TypeCheckout.COINS.value)
                }
                else -> {
                    makeText(getString(R.string.please_select_payment))
                }
            }
        }
    }

    private fun createOrderWithTypeCheckout(type: String) {
        arguments?.run {
            if (getParcelable<Address>(BUNDLE_ADDRESS) == null)
                makeText(getString(R.string.notification_choose_address))
            getParcelable<Partner>(BUNDLE_PARTNER)?.let { partner ->
                getParcelable<User>(BUNDLE_USER)?.let { user ->
                    getParcelable<Address>(BUNDLE_ADDRESS)?.let { address ->
                        getParcelable<DistanceResponse>(BUNDLE_DISTANCE)?.let { distance ->
                            arguments?.getParcelable<VoucherItem>(BUNDLE_VOUCHER)
                                .let { voucherItem ->
                                    if (voucherItem != null) {
                                        OrderVoucherRequest(
                                            user,
                                            address,
                                            partner,
                                            distance,
                                            type,
                                            voucherItem.voucher
                                        ).also {
                                            presenter.createOrderWithVoucher(it)
                                            dialogQueryShipping.show(childFragmentManager, null)
                                        }
                                    } else {
                                        OrderRequest(user, address, partner, distance, type).also {
                                            presenter.createOrder(it)
                                            dialogQueryShipping.show(childFragmentManager, null)
                                        }
                                    }
                                }
                        }
                    }
                }
            }
        }
    }

    private fun showDialogRequireAddress() {
        LocationHelper.showPositiveDialogWithListener(
            requireContext(),
            getString(R.string.notification),
            getString(R.string.notification_address_order),
            object : IPositiveNegativeListener {

                override fun onPositive() {
                    addFragmentBackStack(AddressFragment.newInstance(), R.id.containerView)
                }
            },
            getString(R.string.answer_ok),
            false
        )
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

    private fun setTotal(total: Float) {
        totalPlaceOrderTextView.text = total.toString().currencyVn()
        priceTotalTextView.text = total.toString().currencyVn()
    }

    companion object {
        const val BUNDLE_PARTNER = "BUNDLE_PARTNER"
        const val BUNDLE_VOUCHER = "BUNDLE_VOUCHER"
        const val BUNDLE_VOUCHERS = "BUNDLE_VOUCHERS"
        const val BUNDLE_TOTAL = "BUNDLE_TOTAL"
        const val BUNDLE_USER = "BUNDLE_USER"
        const val BUNDLE_ADDRESS = "BUNDLE_ADDRESS"
        const val BUNDLE_DISTANCE = "BUNDLE_DISTANCE "

        fun newInstance(bundle: Bundle?) = CheckoutFragment().apply {
            arguments = bundle
        }
    }
}
