package com.sun.qakhadelivery.screens.address

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Address
import com.sun.qakhadelivery.screens.address.adapter.AddressAdapter
import com.sun.qakhadelivery.screens.address.adapter.AddressAdapterOnClickListener
import kotlinx.android.synthetic.main.fragment_address.*

class AddressFragment : Fragment(), AddressAdapterOnClickListener {

    private val adapter: AddressAdapter by lazy {
        AddressAdapter()
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
        handleEvents()
    }

    override fun onItemClickListener(address: Address) {
        Log.e("address", address.name)
    }

    private fun initViews() {
        recyclerViewAddress.adapter = adapter.apply {
            registerListener(this@AddressFragment)
        }
    }

    private fun handleEvents() {
        imageViewBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        fun newInstance() = AddressFragment()
    }
}
