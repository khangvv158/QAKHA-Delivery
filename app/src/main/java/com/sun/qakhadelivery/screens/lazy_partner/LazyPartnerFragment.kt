package com.sun.qakhadelivery.screens.lazy_partner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.data.repository.PartnerRepositoryImpl
import com.sun.qakhadelivery.data.source.remote.schema.response.PartnerResponse
import com.sun.qakhadelivery.extensions.*
import com.sun.qakhadelivery.screens.home.HomeFragment.Companion.BUNDLE_PARTNER
import com.sun.qakhadelivery.screens.partner.PartnerFragment
import kotlinx.android.synthetic.main.fragment_lazy_loading.*

class LazyPartnerFragment : Fragment(), LazyPartnerContract.View {

    private val presenter by lazy {
        LazyPartnerPresenter(PartnerRepositoryImpl.getInstance())
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
            setView(this@LazyPartnerFragment)
            arguments?.getParcelable<Partner>(BUNDLE_PARTNER)?.let {
                presenter.getPartnerById(it.id)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onErrorGetPartnerById(exception: String) {
        makeText(exception)
        loadingProgress.gone()
        merchantErrorTextView.show()
        pendingImageView.show()
        descriptionErrorTextView.show()
        buttonBack.show()
    }

    override fun onSuccessGetPartnerById(partnerResponse: PartnerResponse) {
        parentFragmentManager.popBackStack()
        addFragmentBackStack(PartnerFragment.newInstance(partnerResponse), R.id.containerView)
    }

    private fun handleEvents() {
        buttonBack.setOnSafeClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        fun newInstance(partner: Partner) = LazyPartnerFragment().apply {
            arguments = bundleOf(BUNDLE_PARTNER to partner)
        }
    }
}
