package com.sun.qakhadelivery.screens.address.adapter

import com.sun.qakhadelivery.data.model.Address

interface AddressAdapterOnClickListener {

    fun onItemClickListener(address: Address)
    fun onEditItemClickListener(address: Address)
    fun onItemLongClickListener(address: Address)
}
