package com.sun.qakhadelivery.screens.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Product
import com.sun.qakhadelivery.extensions.currencyVn
import com.sun.qakhadelivery.extensions.loadUrlOrigin
import kotlinx.android.synthetic.main.fragment_product.*

class ProductFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        handleEvent()
    }

    private fun handleEvent() {
        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun initViews() {
        arguments?.getParcelable<Product>(BUNDLE_PRODUCT)?.let {
            productImageView.loadUrlOrigin(it.image.imageUrl)
            descriptionProductTextView.text = it.description
            priceTextView.text = it.price.toString().currencyVn()
            nameProductTextView.text = it.name
        }
    }

    companion object {
        private const val BUNDLE_PRODUCT = "BUNDLE_PRODUCT"

        fun newInstance(product: Product) = ProductFragment().apply {
            arguments = Bundle().apply {
                putParcelable(BUNDLE_PRODUCT, product)
            }
        }
    }
}
