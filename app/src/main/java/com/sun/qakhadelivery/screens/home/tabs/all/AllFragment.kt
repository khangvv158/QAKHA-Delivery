package com.sun.qakhadelivery.screens.home.tabs.all

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.data.model.TypePartner
import com.sun.qakhadelivery.data.repository.PartnerRepositoryImpl
import com.sun.qakhadelivery.extensions.gone
import com.sun.qakhadelivery.extensions.show
import com.sun.qakhadelivery.screens.home.HomeFragment.Companion.BUNDLE_PARTNER
import com.sun.qakhadelivery.screens.home.HomeFragment.Companion.REQUEST_KEY
import com.sun.qakhadelivery.screens.home.tabs.all.adapter.PartnerAdapter
import com.sun.qakhadelivery.utils.Constants
import com.sun.qakhadelivery.utils.OnItemRecyclerViewClickListener
import kotlinx.android.synthetic.main.fragment_all.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class AllFragment : Fragment(),
    AllContract.View,
    OnItemRecyclerViewClickListener<Partner> {

    private val adapter: PartnerAdapter by lazy {
        PartnerAdapter()
    }
    private val presenter by lazy {
        AllPresenter(PartnerRepositoryImpl())
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
        return inflater.inflate(R.layout.fragment_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun onSuccessGetPartners(partners: MutableList<Partner>) {
        loadingProgress?.gone()
        adapter.updateData(partners)
    }

    override fun onErrorGetPartners(exception: String) {
        loadingProgress?.gone()
    }

    override fun onSuccessGetPartnersByIdType(partners: MutableList<Partner>) {
        loadingProgress?.gone()
        adapter.updateData(partners)
    }

    private fun initViews() {
        recyclerViewPartnerAll.adapter = adapter.apply {
            registerRecyclerViewListener(this@AllFragment)
        }
    }

    private fun initData() {
        presenter.run {
            setView(this@AllFragment)
            getPartners()
            loadingProgress?.show()
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun eventBusGetTypePartner(typePartner: TypePartner) {
        if (typePartner.id != Constants.ID_PARTNER_ALL) {
            recyclerViewPartnerAll.layoutManager?.scrollToPosition(0)
            presenter.getPartnersByIdType(typePartner.id)
            loadingProgress?.show()
        } else {
            recyclerViewPartnerAll.layoutManager?.scrollToPosition(0)
            presenter.getPartners()
            loadingProgress?.show()
        }
    }

    override fun onItemClickListener(item: Partner?) {
        item?.let {
            parentFragmentManager.setFragmentResult(
                REQUEST_KEY,
                bundleOf(BUNDLE_PARTNER to item)
            )
        }
    }

    companion object {

        fun newInstance() = AllFragment()
    }
}
