package com.sun.qakhadelivery.screens.chooseaddress

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.repository.AddressRepositoryImpl
import com.sun.qakhadelivery.data.repository.TokenRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.extensions.back
import com.sun.qakhadelivery.extensions.makeText
import com.sun.qakhadelivery.utils.GoogleMapHelper
import kotlinx.android.synthetic.main.fragment_choose_address.*
import java.util.*

class ChooseAddressFragment : Fragment(), ChooseAddressContract.View {

    private val geo: Geocoder by lazy {
        Geocoder(requireContext(), Locale.getDefault())
    }
    private val googleMapHelper by lazy {
        GoogleMapHelper()
    }
    private val presenter by lazy {
        ChooseAddressPresenter(
            AddressRepositoryImpl(),
            TokenRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
            )
        )
    }
    private lateinit var googleMap: GoogleMap
    private var markerFlagLocation: Marker? = null
    private var positionClickable = LatLng(0.0, 0.0)
    private var addressUser: com.sun.qakhadelivery.data.model.Address? = null
    private var onChooseAddressListener: OnChooseAddressListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync {
            setUpGoogleMap(it)
        }
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun onCreateAddressSuccess() {
        makeText(getString(R.string.create_address_successfully))
        onChooseAddressListener?.onCreateAddressSuccess()
        back()
    }

    override fun onUpdateAddressSuccess() {
        makeText(getString(R.string.update_address_successfully))
        onChooseAddressListener?.onUpdateAddressSuccess()
        back()
    }

    override fun onError(message: String) {
        makeText(message)
    }

    @SuppressLint("MissingPermission")
    private fun setUpGoogleMap(googleMap: GoogleMap) {
        this.googleMap = googleMap
        with(this.googleMap) {
            isMyLocationEnabled = true
        }
        initViews()
        handleEvents()
    }

    fun registerOnChooseAddressListener(onChooseAddressListener: OnChooseAddressListener) {
        this.onChooseAddressListener = onChooseAddressListener
    }

    private fun initViews() {
        presenter.setView(this)
        arguments?.getParcelable<com.sun.qakhadelivery.data.model.Address>(BUNDLE_TYPE_CALL_API)
            .let {
                addressUser = it?.apply {
                    nameAddressEditText.setText(
                        name,
                        TextView.BufferType.EDITABLE
                    )
                    val position = LatLng(latitude.toDouble(), longitude.toDouble())
                    animateCamera(position)
                    if (markerFlagLocation == null) {
                        markerFlagLocation =
                            googleMap.addMarker(googleMapHelper.getNormalMarkerOptions(position))
                    }
                }
            }
    }

    private fun handleEvents() {
        googleMap.setOnMapClickListener {
            if (markerFlagLocation == null) {
                markerFlagLocation =
                    googleMap.addMarker(googleMapHelper.getNormalMarkerOptions(it))
            } else {
                markerFlagLocation?.position = it
            }
            positionClickable = it
            try {
                val addresses: List<Address> = geo.getFromLocation(it.latitude, it.longitude, 1)
                if (addresses.isNotEmpty()) {
                    nameAddressEditText.setText(
                        addresses[0].getAddressLine(0),
                        TextView.BufferType.EDITABLE
                    )
                } else {
                    makeText(getString(R.string.waiting_for_location))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        buttonSave.setOnClickListener {
            if (addressUser == null) {
                presenter.createAddress(
                    nameAddressEditText.text.toString(),
                    positionClickable.latitude,
                    positionClickable.longitude
                )
            } else {
                addressUser?.let {
                    presenter.updateAddress(
                        nameAddressEditText.text.toString(),
                        positionClickable.latitude,
                        positionClickable.longitude,
                        it.idAddress
                    )
                }
            }
        }
        imageViewBack.setOnClickListener {
            back()
        }
    }

    private fun animateCamera(latLng: LatLng) {
        val cameraUpdate = googleMapHelper.buildCameraUpdate(latLng)
        googleMap.animateCamera(cameraUpdate, 10, null)
    }

    companion object {
        private const val BUNDLE_TYPE_CALL_API = "BUNDLE_TYPE_CALL_API"

        fun newInstance(address: com.sun.qakhadelivery.data.model.Address?) =
            ChooseAddressFragment().apply {
                arguments = bundleOf(BUNDLE_TYPE_CALL_API to address)
            }
    }
}
