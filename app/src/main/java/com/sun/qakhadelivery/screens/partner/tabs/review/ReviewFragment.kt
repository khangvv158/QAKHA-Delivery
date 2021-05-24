package com.sun.qakhadelivery.screens.partner.tabs.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Feedback
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.data.repository.PartnerRepositoryImpl
import com.sun.qakhadelivery.extensions.makeText
import com.sun.qakhadelivery.screens.home.HomeFragment.Companion.BUNDLE_PARTNER
import com.sun.qakhadelivery.screens.partner.tabs.review.adapter.CommentAdapter
import com.sun.qakhadelivery.utils.BasePageFragment
import com.sun.qakhadelivery.widget.recyclerview.divider.DividerItemDecorator
import kotlinx.android.synthetic.main.fragment_review.*

class ReviewFragment : BasePageFragment(), ReviewContract.View {

    private val presenter by lazy {
        ReviewPresenter(PartnerRepositoryImpl.getInstance())
    }
    private val commentAdapter by lazy {
        CommentAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun fetchData() {
        initData()
    }

    override fun onGetFeedbackSuccess(feedback: Feedback) {
        commentAdapter.updateData(feedback.comments)
    }

    override fun onError(message: String) {
        makeText(message)
    }

    private fun initViews() {
        presenter.setView(this)
        commentRecyclerView.apply {
            adapter = commentAdapter
            ContextCompat.getDrawable(context, R.drawable.divider)?.let {
                addItemDecoration(DividerItemDecorator(it))
            }
        }
    }

    private fun initData() {
        arguments?.getParcelable<Partner>(BUNDLE_PARTNER)?.let {
            presenter.getFeedback(it.id)
        }
    }

    companion object {

        fun newInstance(partner: Partner?) = ReviewFragment().apply {
            arguments = Bundle().apply {
                putParcelable(BUNDLE_PARTNER, partner)
            }
        }
    }
}
