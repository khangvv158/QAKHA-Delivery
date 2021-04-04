package com.sun.qakhadelivery.screens.home.tabs.all

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.data.model.TypePartner
import com.sun.qakhadelivery.screens.home.tabs.all.adapter.AllPartnerAdapter
import com.sun.qakhadelivery.utils.OnItemRecyclerViewClickListener
import kotlinx.android.synthetic.main.fragment_all.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class AllFragment : Fragment(), AllContract.View, OnItemRecyclerViewClickListener<Partner> {

    private val adapter: AllPartnerAdapter by lazy {
        AllPartnerAdapter()
    }
    private var idType = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews() {
        recyclerViewPartnerAll.adapter = adapter.apply {
            registerRecyclerViewListener(this@AllFragment)
        }
    }

    private fun initData() {
        if (idType == -1) {
            val ds = mutableListOf<Partner>().apply {
                add(Partner(0, "Quán 1", "63 Phạm Văn Nghị - Đà nẵng", null, 4.5f, null, null, null, null, null, null, null))
                add(Partner(1, "Quán 2", "01 Phạm Văn Nghị - Đà nẵng", null, 4.7f, null, null, null, null, null, null, null))
                add(Partner(2, "Quán 3", "02 Phạm Văn Nghị - Đà nẵng", null, 4.8f, null, null, null, null, null, null, null))
                add(Partner(3, "Quán 4", "03 Nguyễn Văn Linh - Đà nẵng", null, 4.9f, null, null, null, null, null, null, null))
                add(Partner(4, "Quán 5", "54 Phạm Văn Nghị - Đà nẵng", null, 4.2f, null, null, null, null, null, null, null))
                add(Partner(5, "Quán 6", "55 Phạm Văn Nghị - Đà nẵng", null, 4.4f, null, null, null, null, null, null, null))
                add(Partner(6, "Quán 7", "99 Phạm Văn Nghị - Đà nẵng", null, 4.5f, null, null, null, null, null, null, null))
                add(Partner(7, "Quán 8", "12 Phạm Văn Nghị - Đà nẵng", null, 4.2f, null, null, null, null, null, null, null))
            }
            adapter.updateData(ds)
        }
    }

    private fun getPartnerByIdType(idType: Int) {
        if (idType == 0) {
            val ds = mutableListOf<Partner>().apply {
                add(Partner(0, "Quán 0 - 1", "63 Phạm Văn Nghị - Đà nẵng", null, 4.5f, null, null, null, null, null, null, null))
                add(Partner(1, "Quán 0 - 2", "01 Phạm Văn Nghị - Đà nẵng", null, 4.7f, null, null, null, null, null, null, null))
                add(Partner(2, "Quán 0 - 3", "02 Phạm Văn Nghị - Đà nẵng", null, 4.8f, null, null, null, null, null, null, null))
            }
            adapter.updateData(ds)
        }
        if (idType == 1) {
            val ds = mutableListOf<Partner>().apply {
                add(Partner(0, "Quán 1 - 1", "63 Phạm Văn Nghị - Đà nẵng", null, 4.5f, null, null, null, null, null, null, null))
                add(Partner(1, "Quán 1 - 2", "01 Phạm Văn Nghị - Đà nẵng", null, 4.7f, null, null, null, null, null, null, null))
                add(Partner(2, "Quán 1 - 3", "02 Phạm Văn Nghị - Đà nẵng", null, 4.8f, null, null, null, null, null, null, null))
            }
            adapter.updateData(ds)
        }
        if (idType == 2) {
            val ds = mutableListOf<Partner>().apply {
                add(Partner(0, "Quán 2 - 1", "63 Phạm Văn Nghị - Đà nẵng", null, 4.5f, null, null, null, null, null, null, null))
                add(Partner(1, "Quán 2 - 2", "01 Phạm Văn Nghị - Đà nẵng", null, 4.7f, null, null, null, null, null, null, null))
                add(Partner(2, "Quán 2 - 3", "02 Phạm Văn Nghị - Đà nẵng", null, 4.8f, null, null, null, null, null, null, null))
            }
            adapter.updateData(ds)
        }
        if (idType == 3) {
            val ds = mutableListOf<Partner>().apply {
                add(Partner(0, "Quán 3 - 1", "63 Phạm Văn Nghị - Đà nẵng", null, 4.5f, null, null, null, null, null, null, null))
                add(Partner(1, "Quán 3 - 2", "01 Phạm Văn Nghị - Đà nẵng", null, 4.7f, null, null, null, null, null, null, null))
                add(Partner(2, "Quán 3 - 3", "02 Phạm Văn Nghị - Đà nẵng", null, 4.8f, null, null, null, null, null, null, null))
            }
            adapter.updateData(ds)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventBusGetTypePartner(typePartner: TypePartner) {
        Log.e("typeP", typePartner.name)
        idType = typePartner.id
        initData()
        getPartnerByIdType(typePartner.id)
    }

    override fun onItemClickListener(item: Partner?) {
        Log.e("itemP", item?.name.toString())
    }

    companion object {
        fun newInstance() = AllFragment()
    }
}
