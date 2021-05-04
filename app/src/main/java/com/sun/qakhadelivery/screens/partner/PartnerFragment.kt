package com.sun.qakhadelivery.screens.partner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Cart
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.data.repository.CartRepositoryImpl
import com.sun.qakhadelivery.data.repository.TokenRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.data.source.remote.schema.request.CartRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.PartnerResponse
import com.sun.qakhadelivery.extensions.*
import com.sun.qakhadelivery.screens.cart.CartFragment
import com.sun.qakhadelivery.screens.home.HomeFragment
import com.sun.qakhadelivery.screens.partner.adaper.PartnerPagerAdapter
import com.sun.qakhadelivery.screens.partner.tabs.information.InfoPartnerFragment
import com.sun.qakhadelivery.screens.partner.tabs.product.ProductPartnerFragment
import com.sun.qakhadelivery.screens.partner.tabs.review.ReviewFragment
import com.sun.qakhadelivery.screens.signin.SignInFragment
import com.sun.qakhadelivery.utils.*
import com.sun.qakhadelivery.utils.Constants.DEFAULT_QUANTITY
import kotlinx.android.synthetic.main.fragment_partner.*

class PartnerFragment : Fragment(), PartnerContract.View {

    private val pagerAdapter by lazy {
        PartnerPagerAdapter(
            childFragmentManager,
            requireContext()
        )
    }
    private val presenter by lazy {
        PartnerPresenter(
            CartRepositoryImpl.getInstance(),
            TokenRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
            )
        )
    }
    private val cartBottomSheet by lazy { CartFragment() }
    private var carts = mutableListOf<Cart>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_partner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
        handleEvent()
    }


    override fun onStart() {
        super.onStart()
        presenter.setView(this@PartnerFragment)
        UserUtils.workWithSignIn(requireContext()) {
            arguments?.getParcelable<Partner>(BUNDLE_PARTNER)?.let {
                presenter.getCart(it.id, it.getProducts())
            }
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onSuccessGetCart(carts: MutableList<Cart>) {
        this.carts = carts
        updateQuantity()
        enableInteraction()
    }

    override fun onSuccessCreateCart(carts: MutableList<Cart>) {
        this.carts = carts
        updateQuantity()
        enableInteraction()
    }

    override fun onSuccessUpdateCart(carts: MutableList<Cart>) {
        this.carts = carts
        updateQuantity()
        enableInteraction()
    }

    override fun onErrorGetCart(exception: String) {
        enableInteraction()
    }

    override fun onErrorCreateCart(exception: String) {
        enableInteraction()
    }

    override fun onErrorUpdateCart(exception: String) {
        enableInteraction()
    }

    private fun initViews() {
        initViewPager()
        initTabLayout()
    }

    private fun initData() {
        arguments?.getParcelable<Partner>(HomeFragment.BUNDLE_PARTNER)?.let {
            it.image?.let { image -> partnerImageView.loadUrl(image.imageUrl) }
            titlePartnerTextView.text = it.name
            statusTextView.text = it.status
        }
        arguments?.getParcelable<PartnerResponse>(BUNDLE_PARTNER_RESPONSE)?.let {
            ratePartnerTextView.text = it.avgPoint.toString()
        }
        cartBottomSheet.arguments = arguments
    }

    private fun initTabLayout() {
        partnerTabLayout.setupWithViewPager(partnerViewPager)
    }

    private fun initViewPager() {
        partnerViewPager.apply {
            offscreenPageLimit = OFF_SCREEN_PAGE_LIMIT
            adapter = pagerAdapter.apply {
                addFragment(ProductPartnerFragment.newInstance(arguments))
                addFragment(ReviewFragment.newInstance(arguments))
                addFragment(InfoPartnerFragment.newInstance(arguments))
            }
        }
    }

    private fun handleEvent() {
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        cartFloatButton.setOnSafeClickListener {
            UserUtils.workWithSignIn(requireContext(), {
                if (!cartBottomSheet.isAdded) {
                    cartBottomSheet.show(childFragmentManager, null)
                }
            }, {
                addFragmentSlideAnim(SignInFragment.newInstance(), R.id.containerView)
            })
        }
        cartBottomSheet.setOnCallback(object : CartFragment.CallbackCart {

            override fun onUpdateCart(carts: MutableList<Cart>) {
                this@PartnerFragment.carts = carts
                updateQuantity()
            }

            override fun onClear() {
                carts.clear()
            }
        })
        setFragmentResultListener(ProductPartnerFragment.KEY_REQUEST_PARTNER) { _, bundle ->
            bundle.getParcelable<Product>(ProductPartnerFragment.BUNDLE_PRODUCT)
                ?.let { product ->
                    UserUtils.workWithSignIn(requireContext(), {
                        arguments?.getParcelable<Partner>(BUNDLE_PARTNER)?.let {
                            val products = it.categories
                                .flatMap { category -> category.products }
                                .toMutableList()
                            updateOrInsertCart(product, products)
                        }
                    }, {
                        addFragmentSlideAnim(SignInFragment.newInstance(), R.id.containerView)
                    })
                }
        }
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (loadingProgress.isVisible) {
                        enableInteraction()
                    } else {
                        remove()
                        activity?.onBackPressed()
                    }
                }
            })
    }

    private fun updateOrInsertCart(
        product: Product,
        products: MutableList<Product>
    ) {
        val cart = carts.find { it.product.id == product.id }
        if (cart != null) {
            val cartRequest = CartRequest(
                cart.product.id,
                cart.quantity,
                cart.partnerId
            ).apply {
                quantity += DEFAULT_QUANTITY
            }
            presenter.updateCart(cartRequest, products)
            disableInteraction()
        } else {
            arguments?.getParcelable<Partner>(BUNDLE_PARTNER)?.let {
                presenter.createCart(
                    CartRequest(product.id, DEFAULT_QUANTITY, it.id),
                    products
                )
                disableInteraction()
            }
        }
    }

    private fun updateQuantity() {
        if (carts.size < DEFAULT_QUANTITY) {
            floatButtonLayout.gone()
        } else {
            floatButtonLayout.show()
        }
        val shake: Animation = AnimationUtils.loadAnimation(context, R.anim.animation_shake)
        counterCartTextView.apply {
            text = carts.map { it.quantity }.sum().toString()
            startAnimation(shake)
        }
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
        private const val BUNDLE_PARTNER_RESPONSE = "BUNDLE_PARTNER_RESPONSE"
        private const val OFF_SCREEN_PAGE_LIMIT = 3

        fun newInstance(partnerResponse: PartnerResponse) = PartnerFragment().apply {
            arguments = Bundle().apply {
                putParcelable(BUNDLE_PARTNER, partnerResponse.partner)
                putParcelable(BUNDLE_PARTNER_RESPONSE, partnerResponse)
            }
        }
    }
}
