package com.sun.qakhadelivery.screens.shippingdetail

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.SupportMapFragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.source.remote.schema.response.OrderResponse
import com.sun.qakhadelivery.utils.GoogleMapHelper

class ShippingDetailFragment : Fragment(),ShippingDetailContract.View {

    private lateinit var googleMap: GoogleMap
    private lateinit var orderResponse: OrderResponse
    private val googleMapHelper by lazy {
        GoogleMapHelper()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getParcelable<OrderResponse>(BUNDLE_ORDER_RESPONSE)?.let {
            orderResponse = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shipping_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        MapsInitializer.initialize(requireContext())
        mapFragment?.getMapAsync {
            setUpGoogleMap(it)
        }
    }

    @SuppressLint("MissingPermission")
    private fun setUpGoogleMap(map: GoogleMap) {
        googleMap = map
        googleMap.isMyLocationEnabled = true
        initViews()
        fetchDataLocationDriver()
        handleEvents()
    }

    private fun initViews() {
    }

    private fun fetchDataLocationDriver() {
    }

    private fun handleEvents() {
    }

    companion object {

        private const val BUNDLE_ORDER_RESPONSE = "BUNDLE_ORDER_RESPONSE"

        fun newInstance(orderResponse: OrderResponse) = ShippingDetailFragment().apply {
            arguments = bundleOf(BUNDLE_ORDER_RESPONSE to orderResponse)
        }
    }
}
