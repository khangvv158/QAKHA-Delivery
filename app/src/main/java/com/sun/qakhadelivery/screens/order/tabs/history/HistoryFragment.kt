package com.sun.qakhadelivery.screens.order.tabs.history

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Event
import com.sun.qakhadelivery.data.model.Order
import com.sun.qakhadelivery.screens.order.tabs.history.adapter.HistoryAdapter
import com.sun.qakhadelivery.screens.orderdetail.OrderDetailFragment
import com.sun.qakhadelivery.utils.addFragmentBackStack
import com.sun.qakhadelivery.utils.getOrderHistory
import kotlinx.android.synthetic.main.fragment_history.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HistoryFragment : Fragment() {

    private val historyAdapter: HistoryAdapter by lazy {
        HistoryAdapter()
    }

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
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initData()
    }

    private fun initRecyclerView() {
        recyclerViewHistory.adapter = historyAdapter
    }

    private fun initData() {
        historyAdapter.updateData(getOrderHistory())
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRecyclerViewHistoryClick(event: Event<Order>) {
        event.apply {
            if (keyEvent == HistoryAdapter.EVENT_HISTORY) {
                addFragmentBackStack(OrderDetailFragment.newInstance(),R.id.containerView)
            }
        }
    }

    companion object {
        fun newInstance() = HistoryFragment()
    }
}
