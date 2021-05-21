package com.sun.qakhadelivery.screens.partner.tabs.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.utils.BasePageFragment
import com.sun.qakhadelivery.utils.GoogleMapHelper
import kotlinx.android.synthetic.main.fragment_info_partner.*

class InfoPartnerFragment : BasePageFragment() {

    private lateinit var googleMap: GoogleMap
    private val googleMapHelper by lazy {
        GoogleMapHelper()
    }
    private var marker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info_partner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync {
            googleMap = it
        }
    }

    override fun fetchData() {
        initViews()
    }

    private fun initViews() {
        arguments?.getParcelable<Partner>(BUNDLE_PARTNER)?.let {
            addressTextView.text = it.address
            phonenumberTextView.text = it.phoneNumber
            emailTextView.text = it.email
            timeOpenTextView.text = it.timeOpen
            timeCloseTextView.text = it.timeClose
            LatLng(it.latitude.toDouble(), it.longitude.toDouble()).also { latLng ->
                marker = googleMap.addMarker(
                    googleMapHelper.getNormalMarkerOptions(latLng)
                )
                googleMap.animateCamera(googleMapHelper.buildCameraUpdate(latLng))
            }
        }
    }

    companion object {
        private const val BUNDLE_PARTNER = "BUNDLE_PARTNER"

        fun newInstance(partner: Partner?) = InfoPartnerFragment().apply {
            arguments = Bundle().apply {
                putParcelable(BUNDLE_PARTNER, partner)
            }
        }
    }
}
