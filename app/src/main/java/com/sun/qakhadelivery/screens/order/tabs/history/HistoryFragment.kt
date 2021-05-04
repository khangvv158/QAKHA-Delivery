package com.sun.qakhadelivery.screens.order.tabs.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Refresh
import com.sun.qakhadelivery.data.repository.HistoryRepositoryImpl
import com.sun.qakhadelivery.data.repository.TokenRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.data.source.remote.schema.response.HistoryResponse
import com.sun.qakhadelivery.extensions.addFragmentBackStack
import com.sun.qakhadelivery.extensions.makeText
import com.sun.qakhadelivery.screens.order.tabs.history.adapter.HistoryAdapter
import com.sun.qakhadelivery.screens.orderdetail.OrderDetailFragment
import kotlinx.android.synthetic.main.fragment_history.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HistoryFragment : Fragment(), HistoryContract.View {

    private val historyAdapter by lazy { HistoryAdapter() }
    private val presenter by lazy {
        HistoryPresenter(
            TokenRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
            ),
            HistoryRepositoryImpl.getInstance()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        handleEvent()
    }

    override fun onStart() {
        super.onStart()
        presenter.run {
            setView(this@HistoryFragment)
            presenter.getHistory()
        }
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onSuccessGetHistory(history: MutableList<HistoryResponse>) {
        historyAdapter.updateData(history)
        refreshLayout.isRefreshing = false
    }

    override fun onErrorGetHistory(exception: String) {
        refreshLayout.isRefreshing = false
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onLoadRefresh(refresh: Refresh) {
        refresh.message(this::class.java) {
            refreshLayout.isRefreshing = true
            presenter.getHistory()
        }
    }

    private fun initViews() {
        initRecyclerView()
    }

    private fun handleEvent() {
        historyAdapter.setOnItemClick {
            parentFragment?.addFragmentBackStack(OrderDetailFragment.newInstance(
                Bundle().apply {
                    putParcelable(BUNDLE_HISTORY, it)
                }
            ), R.id.containerView)
        }
        refreshLayout.setOnRefreshListener {
            presenter.getHistory()
        }
    }

    private fun initRecyclerView() {
        recyclerViewHistory.adapter = historyAdapter
    }

    companion object {
        const val BUNDLE_HISTORY = "BUNDLE_HISTORY"

        fun newInstance() = HistoryFragment()
    }
}
