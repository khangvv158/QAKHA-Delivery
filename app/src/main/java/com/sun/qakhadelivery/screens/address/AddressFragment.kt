package com.sun.qakhadelivery.screens.address

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.sun.qakhadelivery.utils.IPositiveNegativeListener
import kotlinx.android.synthetic.main.fragment_address.*

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

    override fun onItemClickListener(address: Address) {
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
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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
        addFragmentSlideAnim(
            ChooseAddressFragment.newInstance(address).apply {
                registerOnChooseAddressListener(this@AddressFragment)
            },
            R.id.containerView
        )
    }

    companion object {
        fun newInstance() = AddressFragment()
    }

}
