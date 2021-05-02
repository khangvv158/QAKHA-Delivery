package com.sun.qakhadelivery.screens.profile.update.email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R

class UpdateEmailFragment : Fragment(), UpdateEmailContract.View {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update_email, container, false)
    }

    companion object {

        fun newInstance() = UpdateEmailFragment()
    }
}
