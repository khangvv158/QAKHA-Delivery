package com.sun.qakhadelivery.screens.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.extensions.loadUrl
import com.sun.qakhadelivery.extensions.setOnSafeClickListener
import com.sun.qakhadelivery.utils.Constants
import com.sun.qakhadelivery.utils.OnItemRecyclerViewClickListener
import kotlinx.android.synthetic.main.item_layout_partner.view.*

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>(), Filterable {

    private var filterPartners = mutableListOf<Partner>()
    private var partners = mutableListOf<Partner>()
    private var listener: OnItemRecyclerViewClickListener<Partner>? = null
    private val searchFilter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filtered = mutableListOf<Partner>()
            if (constraint.isNullOrBlank()) {
                filterPartners.addAll(partners)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim()
                for (partner in partners) {
                    if (partner.name.toLowerCase().contains(filterPattern)) {
                        filtered.add(partner)
                    }
                }
            }
            val filterResult = FilterResults()
            filterResult.values = filtered
            return filterResult
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filterPartners.clear()
            filterPartners.addAll(results?.values as MutableList<Partner>)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_partner, parent, false)
        return ViewHolder(view).apply {
            registerItemViewHolderListener {
                listener?.onItemClickListener(filterPartners[it])
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filterPartners[position])
    }

    override fun getItemCount() = filterPartners.size

    fun updateData(partners: MutableList<Partner>) {
        partners.let {
            this.partners.clear()
            this.partners.addAll(it)
        }
    }

    fun registerRecyclerViewListener(listener: OnItemRecyclerViewClickListener<Partner>) {
        this.listener = listener
    }

    override fun getFilter(): Filter {
        return searchFilter
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun registerItemViewHolderListener(listener: (Int) -> Unit) {
            itemView.setOnSafeClickListener {
                listener(adapterPosition)
            }
        }

        fun bind(partner: Partner) {
            with(itemView) {
                partner.image?.let { imageViewPartner.loadUrl(it.imageUrl) }
                textViewNamePartner?.text = partner.name
                textViewAddressPartner?.text = partner.address
                textViewRatePartner?.text = partner.rate.toString()
                if (partner.distance != Constants.DEFAULT_FLOAT) {
                    textViewKilometer.text = partner.distance.toString()
                }
                if (partner.avgPoint != Constants.DEFAULT_FLOAT) {
                    textViewRatePartner.text = partner.avgPoint.toString()
                }
            }
        }
    }
}
