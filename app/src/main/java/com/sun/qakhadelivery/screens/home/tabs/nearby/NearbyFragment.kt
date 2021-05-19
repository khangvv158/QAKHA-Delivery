package com.sun.qakhadelivery.screens.home.tabs.nearby

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.data.model.TypePartner
import com.sun.qakhadelivery.data.repository.PartnerRepositoryImpl
import com.sun.qakhadelivery.data.source.remote.schema.request.LocationRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.PartnerResponse
import com.sun.qakhadelivery.extensions.addFragmentBackStack
import com.sun.qakhadelivery.extensions.gone
import com.sun.qakhadelivery.extensions.show
import com.sun.qakhadelivery.screens.home.tabs.all.adapter.PartnerAdapter
import com.sun.qakhadelivery.screens.partner.PartnerFragment
import com.sun.qakhadelivery.utils.Constants
import com.sun.qakhadelivery.utils.OnItemRecyclerViewClickListener
import kotlinx.android.synthetic.main.fragment_nearby.*
import kotlinx.android.synthetic.main.fragment_nearby.loadingProgress
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class NearbyFragment : Fragment(), NearbyContract.View, OnItemRecyclerViewClickListener<Partner> {

    private val presenter by lazy {
        NearbyPresenter(
            PartnerRepositoryImpl.getInstance()
        )
    }
    private val partnerAdapter by lazy {
        PartnerAdapter()
    }
    private var currentLatLng = LatLng(0.0, 0.0)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nearby, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setView(this)
        initViews()
        initData()
    }

    override fun getSuggestPartnerNearbySuccess(partners: MutableList<Partner>) {
        partnerAdapter.updateData(partners)
        loadingProgress?.gone()
    }

    override fun getPartnerByIdSuccess(partnerResponse: PartnerResponse) {
        parentFragment?.addFragmentBackStack(
            PartnerFragment.newInstance(partnerResponse),
            R.id.containerView
        )
    }

    override fun onError(message: String) {
        loadingProgress?.gone()
    }

    override fun onItemClickListener(item: Partner?) {
        item?.let { presenter.getPartnerById(it.id) }
    }

    private fun initViews() {
        recyclerViewPartnerNearby.adapter = partnerAdapter.apply {
            registerRecyclerViewListener(this@NearbyFragment)
        }
    }

    private fun initData() {
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun eventBusGetTypePartner(typePartner: TypePartner) {
        if (typePartner.id != Constants.ID_PARTNER_ALL) {
            presenter.getSuggestPartnerNearby(
                LocationRequest(
                    currentLatLng.latitude.toFloat(),
                    currentLatLng.longitude.toFloat()
                )
            )
            recyclerViewPartnerNearby.layoutManager?.scrollToPosition(0)
            loadingProgress?.show()
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun eventBusGetLocationRequest(location: LatLng) {
        currentLatLng = location
        presenter.getSuggestPartnerNearby(
            LocationRequest(
                currentLatLng.latitude.toFloat(),
                currentLatLng.longitude.toFloat()
            )
        )
        recyclerViewPartnerNearby.layoutManager?.scrollToPosition(0)
        loadingProgress?.show()
    }

    companion object {

        fun newInstance() = NearbyFragment()
    }
}
