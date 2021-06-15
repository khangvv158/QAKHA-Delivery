package com.sun.qakhadelivery.screens.navigate.helpcenter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import kotlinx.android.synthetic.main.fragment_help_center.*

class HelpCenterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_help_center, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEvents()
    }

    private fun handleEvents() {
        imageViewBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        callImageView.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:0396355253")
                )
            )
        }
        gmailImageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("qakhadelivery@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Feedback")
            }
            startActivity(intent)
        }
    }

    companion object {
        fun newInstance() = HelpCenterFragment()
    }
}
