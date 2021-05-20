package com.sun.qakhadelivery.screens.main

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.SearchView
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.repository.LanguageRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.extensions.addFragment
import com.sun.qakhadelivery.screens.container.ContainerFragment
import java.util.*

class MainActivity : AppCompatActivity(), MainContract.View {

    private val presenter by lazy {
        MainPresenter(
            LanguageRepositoryImpl.getInstance(SharedPrefsImpl.getInstance(baseContext))
        )
    }
    private var doubleBackPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.setView(this)
        presenter.onSetupLanguage()
        setFlags()
        initViews()
    }

    override fun onSetupLanguageSuccess(langCode: String) {
        setAppLocale(langCode)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            super.onBackPressed()
        } else if (!doubleBackPressed) {
            this.doubleBackPressed = true
            Toast.makeText(
                this,
                getString(R.string.back_again_to_exit),
                Toast.LENGTH_SHORT
            ).show()
            Handler().postDelayed(Runnable { doubleBackPressed = false }, 2000)
            return
        } else {
            super.onBackPressed()
            return
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            currentFocus?.let {
                if (it is AppCompatEditText) {
                    val outRect = Rect()
                    it.getGlobalVisibleRect(outRect)
                    if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                        it.clearFocus()
                        hideKeyboard(it)
                    }
                }
                if (it is SearchView.SearchAutoComplete) {
                    val viewRect = Rect()
                    it.getGlobalVisibleRect(viewRect)
                    if (!viewRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                        hideKeyboard(it)
                        it.clearFocus()
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun initViews() {
        addFragment(ContainerFragment.newInstance(), R.id.containerView)
    }

    private fun setFlags() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setAppLocale(localeCode: String) {
        val resources: Resources = resources
        val dm: DisplayMetrics = resources.displayMetrics
        val config: Configuration = resources.configuration
        config.setLocale(Locale(localeCode.toLowerCase()))
        resources.updateConfiguration(config, dm)
    }
}
