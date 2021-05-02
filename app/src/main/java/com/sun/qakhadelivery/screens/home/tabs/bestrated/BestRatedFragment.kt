package com.sun.qakhadelivery.screens.home.tabs.bestrated

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.data.model.TypePartner
import com.sun.qakhadelivery.data.repository.PartnerRepositoryImpl
import com.sun.qakhadelivery.data.source.remote.schema.response.PartnerResponse
import com.sun.qakhadelivery.extensions.addFragmentBackStack
import com.sun.qakhadelivery.extensions.gone
import com.sun.qakhadelivery.extensions.makeText
import com.sun.qakhadelivery.extensions.show
import com.sun.qakhadelivery.screens.home.tabs.all.adapter.PartnerAdapter
import com.sun.qakhadelivery.screens.partner.PartnerFragment
import com.sun.qakhadelivery.utils.Constants
import com.sun.qakhadelivery.utils.OnItemRecyclerViewClickListener
import kotlinx.android.synthetic.main.fragment_best_rated.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class BestRatedFragment : Fragment(), BestRatedContract.View,
    OnItemRecyclerViewClickListener<Partner> {

    private val presenter by lazy {
        BestRatedPresenter(
            PartnerRepositoryImpl.getInstance()
        )
    }
    private val bestRatedAdapter by lazy {
        PartnerAdapter()
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
        return inflater.inflate(R.layout.fragment_best_rated, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
    }

    override fun getSuggestPartnerBestRatedSuccess(partners: MutableList<Partner>) {
        bestRatedAdapter.updateData(partners)
        loadingProgress.gone()
    }

    override fun getPartnerByIdSuccess(partnerResponse: PartnerResponse) {
        parentFragment?.addFragmentBackStack(
            PartnerFragment.newInstance().apply {
                arguments = Bundle().apply {
                    putParcelable(PartnerFragment.BUNDLE_PARTNER, partnerResponse.partner)
                }
            },
            R.id.containerView
        )
    }

    override fun onError(message: String) {
        makeText(message)
        loadingProgress.gone()
    }

    override fun onItemClickListener(item: Partner?) {
        item?.let { presenter.getPartnerById(it.id) }
    }

    private fun initViews() {
        presenter.setView(this)
        recyclerViewPartnerBestRated.adapter = bestRatedAdapter.apply {
            registerRecyclerViewListener(this@BestRatedFragment)
        }
    }

    private fun initData() {
        presenter.getSuggestPartnerBestRated()
        loadingProgress.show()
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun eventBusGetTypePartner(typePartner: TypePartner) {
        if (typePartner.id != Constants.ID_PARTNER_ALL) {
            presenter.getSuggestPartnerBestRated()
            loadingProgress.show()
        } else {
            loadingProgress.show()
        }
    }

    companion object {
        fun newInstance() = BestRatedFragment()
    }
}
