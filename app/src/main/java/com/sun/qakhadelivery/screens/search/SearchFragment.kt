package com.sun.qakhadelivery.screens.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.data.repository.PartnerRepositoryImpl
import com.sun.qakhadelivery.data.source.remote.schema.response.PartnerResponse
import com.sun.qakhadelivery.extensions.*
import com.sun.qakhadelivery.screens.partner.PartnerFragment
import com.sun.qakhadelivery.screens.search.adapter.SearchAdapter
import com.sun.qakhadelivery.utils.OnItemRecyclerViewClickListener
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(), SearchContract.View, OnItemRecyclerViewClickListener<Partner> {

    private val presenter by lazy {
        SearchPresenter(
            PartnerRepositoryImpl.getInstance()
        )
    }
    private val searchAdapter by lazy {
        SearchAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
        handleEvents()
        setupSearch()
    }

    private fun setupSearch() {
        with(searchView) {
            isFocusable = true
            requestFocus()
        }
    }

    override fun getPartnersSuccess(partners: MutableList<Partner>) {
        searchAdapter.updateData(partners)
    }

    override fun getPartnerByIdSuccess(partnerResponse: PartnerResponse) {
        addFragmentBackStack(
            PartnerFragment.newInstance(partnerResponse),
            R.id.containerView
        )
    }

    override fun onError(message: String) {
        makeText(message)
    }

    override fun onItemClickListener(item: Partner?) {
        hideKeyboard()
        item?.let { presenter.getPartnerById(it.id) }
    }

    private fun initData() {
        presenter.getPartners()
    }

    private fun initViews() {
        presenter.setView(this)
        searchRecyclerView.adapter = searchAdapter.apply {
            registerRecyclerViewListener(this@SearchFragment)
        }
    }

    private fun handleEvents() {
        imageViewBack.setOnClickListener {
            hideKeyboard()
            back()
        }
        searchView.setOnQueryTextListener(object : OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchAdapter.filter.filter(newText)
                return false
            }
        })
        searchView.setOnQueryTextFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                showKeyboard(view.findFocus())
            }
        }
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}
    