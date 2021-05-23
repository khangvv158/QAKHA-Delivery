package com.sun.qakhadelivery.screens.address

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Address
import com.sun.qakhadelivery.data.repository.AddressRepositoryImpl
import com.sun.qakhadelivery.data.repository.TokenRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.extensions.addFragmentSlideAnim
import com.sun.qakhadelivery.extensions.makeText
import com.sun.qakhadelivery.extensions.showDialogWithListener
import com.sun.qakhadelivery.screens.address.adapter.AddressAdapter
import com.sun.qakhadelivery.screens.address.adapter.AddressAdapterOnClickListener
import com.sun.qakhadelivery.screens.chooseaddress.ChooseAddressFragment
import com.sun.qakhadelivery.screens.chooseaddress.OnChooseAddressListener
import com.sun.qakhadelivery.screens.home.HomeFragment
import com.sun.qakhadelivery.screens.main.MainActivity
import com.sun.qakhadelivery.utils.IPositiveNegativeListener
import com.sun.qakhadelivery.utils.LocationHelper
import kotlinx.android.synthetic.main.fragment_address.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.greenrobot.eventbus.EventBus

class AddressFragment : Fragment(), AddressAdapterOnClickListener, AddressContract.View,
    OnChooseAddressListener {

    private val adapter: AddressAdapter by lazy {
        AddressAdapter()
    }
    private val presenter by lazy {
        AddressPresenter(
            AddressRepositoryImpl.getInstance(),
            TokenRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
        handleEvents()
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun onItemClickListener(address: Address) {
        EventBus.getDefault().post(address)
        parentFragmentManager.popBackStack()
    }

    override fun onEditItemClickListener(address: Address) {
        navigateChooseAddress(address)
    }

    override fun onItemLongClickListener(address: Address) {
        requireContext().showDialogWithListener(
            getString(R.string.delete_address), address.name,
            object : IPositiveNegativeListener {
                override fun onPositive() {
                    presenter.deleteAddress(address.idAddress)
                }
            }, getString(R.string.delete), true
        )
    }

    override fun onGetAddressesSuccess(addresses: MutableList<Address>) {
        adapter.updateData(addresses)
    }

    override fun onDeleteAddressSuccess() {
        makeText(getString(R.string.delete_address_successfully))
        presenter.getAddresses()
    }

    override fun onCreateAddressSuccess() {
        presenter.getAddresses()
    }

    override fun onUpdateAddressSuccess() {
        presenter.getAddresses()
    }

    override fun onError(message: String) {
        makeText(message)
    }

    private fun initViews() {
        presenter.setView(this)
        recyclerViewAddress.adapter = adapter.apply {
            registerListener(this@AddressFragment)
        }
    }

    private fun initData() {
        presenter.getAddresses()
    }

    private fun handleEvents() {
        imageViewBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        navAddress.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_menu_nav_address -> navigateChooseAddress(null)
            }
            true
        }
    }

    private fun navigateChooseAddress(address: Address?) {
        checkPermissionPlayServices(address)
    }

    private fun checkPermissionPlayServices(address: Address?) {
        if (!LocationHelper.isPlayServicesAvailable(requireContext())) {
            Toast.makeText(
                requireContext(),
                getString(R.string.play_services_did_not_installed),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            checkPermissionLocation(address)
        }
    }

    @SuppressLint("MissingPermission")
    private fun checkPermissionLocation(address: Address?) {
        if (!LocationHelper.isHaveLocationPermission(requireContext())) {
            ActivityCompat.requestPermissions(
                activity as MainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                HomeFragment.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
            return
        }
        if (LocationHelper.isLocationProviderEnabled(requireContext())) {
            showDialogEnableGPS()
        } else {
            addFragmentSlideAnim(
                ChooseAddressFragment.newInstance(address).apply {
                    registerOnChooseAddressListener(this@AddressFragment)
                },
                R.id.containerView
            )
        }
    }

    private fun showDialogEnableGPS() {
        LocationHelper.showPositiveDialogWithListener(
            requireContext(),
            getString(R.string.enable_gps_service),
            getString(R.string.content_location),
            object : IPositiveNegativeListener {
                override fun onPositive() {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            },
            getString(R.string.turn_on),
            true
        )
    }

    companion object {
        fun newInstance() = AddressFragment()
    }
}
