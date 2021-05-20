package com.sun.qakhadelivery.extensions

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sun.qakhadelivery.R
import java.util.*

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun Fragment.addFragment(fragment: Fragment, id: Int) {
    parentFragmentManager.inTransaction {
        add(id, fragment)
    }
}

fun Fragment.addFragmentBackStack(fragment: Fragment, id: Int) {
    parentFragmentManager.inTransaction {
        add(id, fragment)
        addToBackStack(null)
    }
}

fun Fragment.addFragmentFadeAnim(fragment: Fragment, id: Int) {
    parentFragmentManager.inTransaction {
        setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        add(id, fragment)
        addToBackStack(null)
    }
}

fun Fragment.makeText(content: String) {
    Toast.makeText(this.requireContext(), content, Toast.LENGTH_SHORT).show()
}

fun Fragment.addFragmentSlideAnim(fragment: Fragment, id: Int) {
    parentFragmentManager.inTransaction {
        setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
        add(id, fragment)
        addToBackStack(null)
    }
}

fun Fragment.replaceFragment(fragment: Fragment, id: Int) {
    parentFragmentManager.inTransaction {
        replace(id, fragment)
    }
}

fun Fragment.replaceFragmentBackStack(fragment: Fragment, id: Int) {
    parentFragmentManager.inTransaction {
        replace(id, fragment)
        addToBackStack(null)
    }
}

fun Fragment.removeFragment(fragment: Fragment) {
    parentFragmentManager.inTransaction { remove(fragment) }
}

fun Fragment.upperString(id: Int) = getText(id).toString().toUpperCase(Locale.getDefault())

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Fragment.showKeyboard(view: View) {
    view.let { activity?.showKeyboard(view) }
}

fun Fragment.back() {
    this.parentFragmentManager.popBackStack()
}
