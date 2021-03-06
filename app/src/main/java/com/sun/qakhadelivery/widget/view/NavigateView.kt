package com.sun.qakhadelivery.widget.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.extensions.gone
import com.sun.qakhadelivery.utils.Constants
import com.sun.qakhadelivery.extensions.loadUrl
import com.sun.qakhadelivery.extensions.show
import kotlinx.android.synthetic.main.item_layout_navigate_view.view.*

class NavigateView : RelativeLayout {

    private var mTitle: String? = null
    private var mDescribe: String? = null
    private var mIcon: Int = Constants.NOT_EXISTS
    private var mIsIconEnd = true

    constructor(context: Context) : super(context) {
        initViews()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        retrieveAttributes(attrs)
        initViews()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        retrieveAttributes(attrs)
        initViews()
    }

    fun setTitle(title: String?) {
        mTitle = title
        titleTextView?.text = mTitle
    }

    fun getTitle(): String {
        return mTitle ?: Constants.SPACE_STRING
    }

    fun getDescribe(): String {
        return mDescribe ?: Constants.SPACE_STRING
    }

    fun setDescribe(describe: String?) {
        mDescribe = describe
        describeTextView?.text = mDescribe
    }

    fun setIconUrl(url: String) {
        iconImageView.loadUrl(url)
    }

    fun isIconEnd(boolean: Boolean) {
        if (boolean) {
            arrowRightImageView.setImageResource(R.drawable.ic_next)
        } else {
            arrowRightImageView.setImageResource(android.R.color.transparent)
        }
    }

    private fun initViews() {
        View.inflate(context, R.layout.item_layout_navigate_view, this)
        setTitle(mTitle)
        setDescribe(mDescribe)
        if (mIcon != Constants.NOT_EXISTS) {
            iconImageView.setImageResource(mIcon)
        }
        if (mIsIconEnd) {
            isIconEnd(mIsIconEnd)
        }
    }

    private fun retrieveAttributes(attrs: AttributeSet) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.NavigateView)
        mTitle = typeArray.getString(R.styleable.NavigateView_app_title)
        mDescribe = typeArray.getString(R.styleable.NavigateView_app_describe)
        mIcon = typeArray.getResourceId(
            R.styleable.NavigateView_app_iconStart,
            Constants.NOT_EXISTS
        )
        mIsIconEnd = typeArray.getBoolean(R.styleable.NavigateView_app_isEndIcon, true)
        typeArray.recycle()
    }
}
