package com.sun.qakhadelivery.screens.feedback.partner

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.data.model.Refresh
import com.sun.qakhadelivery.data.repository.FeedbackRepositoryImpl
import com.sun.qakhadelivery.data.repository.TokenRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.data.source.remote.schema.request.RatePartnerRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.RatePartnerResponse
import com.sun.qakhadelivery.extensions.loadUrl
import com.sun.qakhadelivery.extensions.makeText
import com.sun.qakhadelivery.extensions.setOnSafeClickListener
import com.sun.qakhadelivery.screens.order.tabs.history.HistoryFragment
import kotlinx.android.synthetic.main.fragment_partner_feedback.*
import org.greenrobot.eventbus.EventBus

class PartnerFeedbackFragment : Fragment(), PartnerFeedbackContact.View {

    private val presenter by lazy {
        PartnerFeedbackPresenter(
            FeedbackRepositoryImpl.getInstance(),
            TokenRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_partner_feedback, container, false)
    }

    override fun onStart() {
        super.onStart()
        presenter.setView(this@PartnerFeedbackFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        handleEvent()
    }

    override fun onSuccessFeedbackPartner(ratePartnerResponse: RatePartnerResponse) {
        makeText(getString(R.string.rate_success))
        EventBus.getDefault().postSticky(
            Refresh(this::class.java, HistoryFragment::class.java)
        )
        parentFragmentManager.popBackStack()
    }

    override fun onErrorFeedbackPartner(exception: String) {
        makeText(getString(R.string.rate_error))
    }

    private fun initViews() {
        arguments?.getParcelable<Partner>(BUNDLE_PARTNER)?.let {
            it.image?.let { image -> avatarCircleImageView.loadUrl(image.imageUrl) }
            nameShopTextView.text = it.name
        }
    }

    private fun handleEvent() {
        feedbackEditText.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                counterTextView.text = s?.length.toString()
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) = Unit

            override fun afterTextChanged(s: Editable?) = Unit
        })
        ratingDriverBar.setOnRatingBarChangeListener { ratingBar: RatingBar, rating: Float, fromUser: Boolean ->
            if (fromUser) {
                buttonFeedback.apply {
                    isEnabled = fromUser
                    setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.colorCarrotOrange
                        )
                    )
                }
            }
            buttonFeedback.setOnSafeClickListener {
                arguments?.getInt(BUNDLE_ORDER_ID)?.let { orderId ->
                    arguments?.getParcelable<Partner>(BUNDLE_PARTNER)?.let { partner ->
                        presenter.feedbackPartner(
                            RatePartnerRequest(
                                orderId,
                                feedbackEditText.text.toString(),
                                rating.toInt(),
                                partner.id
                            )
                        )
                    }
                }
            }
        }
    }

    companion object {
        private const val BUNDLE_PARTNER = "BUNDLE_PARTNER"
        private const val BUNDLE_ORDER_ID = "BUNDLE_ORDER_ID"

        fun newInstance(
            orderId: Int,
            partner: Partner
        ): PartnerFeedbackFragment {
            return PartnerFeedbackFragment().apply {
                arguments = Bundle().apply {
                    putInt(BUNDLE_ORDER_ID, orderId)
                    putParcelable(BUNDLE_PARTNER, partner)
                }
            }
        }
    }
}
