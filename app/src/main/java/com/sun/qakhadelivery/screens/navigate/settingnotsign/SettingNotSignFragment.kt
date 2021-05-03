package com.sun.qakhadelivery.screens.navigate.settingnotsign

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.repository.LanguageRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.extensions.makeText
import com.sun.qakhadelivery.utils.Constants
import kotlinx.android.synthetic.main.fragment_setting_not_sign.*

class SettingNotSignFragment : Fragment(), SettingNotSignContract.View {

    private val presenter by lazy {
        SettingNotSignPresenter(
            LanguageRepositoryImpl.getInstance(SharedPrefsImpl.getInstance(requireContext()))
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting_not_sign, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        handleEvents()
    }

    override fun onGetCurrentLanguageSuccess(langCode: String) {
        if (langCode == Constants.LANG_VI) {
            nav.menu.getItem(0).subMenu.getItem(0).isChecked = true
        } else {
            nav.menu.getItem(0).subMenu.getItem(1).isChecked = true
        }
    }

    override fun onSetLanguageSuccess() {
        activity?.finish()
        activity?.startActivity(activity?.intent)
    }

    override fun onError(message: String) {
        makeText(message)
    }

    private fun initViews() {
        presenter.setView(this)
        presenter.getCurrentLanguage()
    }

    private fun handleEvents() {
        nav.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.itemLangVi -> presenter.setLanguage(LANG_CODE_VI)
                R.id.itemLangEn -> presenter.setLanguage(LANG_CODE_EN)
            }
            true
        }
        imageViewBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {

        private const val LANG_CODE_VI = "vi"
        private const val LANG_CODE_EN = "en"

        fun newInstance() = SettingNotSignFragment()
    }
}
