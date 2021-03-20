package com.sun.qakhadelivery.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.util.*

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun Fragment.addFragment(fragment: Fragment, id: Int) {
    parentFragmentManager.inTransaction {
        add(id, fragment)
        addToBackStack(null)
    }
}

fun Fragment.replaceFragment(fragment: Fragment, id: Int) {
    parentFragmentManager.inTransaction {
        replace(id, fragment)
        addToBackStack(null)
    }
}

fun Fragment.removeFragment(fragment: Fragment) {
    parentFragmentManager.inTransaction { remove(fragment) }
}

fun Fragment.upperString(id: Int) = getText(id).toString().toUpperCase(Locale.getDefault())
