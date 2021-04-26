package com.sun.qakhadelivery.widget.recyclerview.item

import com.sun.qakhadelivery.data.model.OrderDetails
import com.sun.qakhadelivery.widget.recyclerview.CustomRecyclerView

data class OrderItem(val details: OrderDetails) : CustomRecyclerView.Item()
