package com.sun.qakhadelivery.screens.feedback.driver

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
import com.sun.qakhadelivery.data.model.DriverNearest
import com.sun.qakhadelivery.data.model.Partner
import com.sun.qakhadelivery.data.model.Refresh
import com.sun.qakhadelivery.data.repository.FeedbackRepositoryImpl
import com.sun.qakhadelivery.data.repository.TokenRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.data.source.remote.schema.request.RateDriverRequest
import com.sun.qakhadelivery.data.source.remote.schema.response.RateDriverResponse
import com.sun.qakhadelivery.extensions.addFragmentFadeAnim
import com.sun.qakhadelivery.extensions.loadUrl
import com.sun.qakhadelivery.extensions.makeText
import com.sun.qakhadelivery.extensions.setOnSafeClickListener
import com.sun.qakhadelivery.screens.feedback.partner.PartnerFeedbackFragment
import com.sun.qakhadelivery.screens.order.tabs.history.HistoryFragment
import kotlinx.android.synthetic.main.fragment_driver_feedback.*
import org.greenrobot.eventbus.EventBus

class DriverFeedbackFragment : Fragment(), DriverFeedbackContact.View {

    private val presenter by lazy {
        DriverFeedbackPresenter(
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
        return inflater.inflate(R.layout.fragment_driver_feedback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        handleEvent()
    }

    override fun onSuccessFeedback(rateDriverResponse: RateDriverResponse) {
        makeText(getString(R.string.rate_success))
        EventBus.getDefault().postSticky(
            Refresh(this::class.java, HistoryFragment::class.java)
        )
        parentFragmentManager.popBackStack()
        arguments?.getParcelable<Partner>(BUNDLE_PARTNER)?.let {
            addFragmentFadeAnim(
                PartnerFeedbackFragment.newInstance(
                    rateDriverResponse.orderId,
                    it
                ), R.id.containerView
            )
        }
    }

    override fun onErrorFeedback(exception: String) {
        makeText(getString(R.string.rate_error))
    }

    private fun initViews() {
        arguments?.getParcelable<DriverNearest>(BUNDLE_DRIVER)?.let {
            avatarCircleImageView.loadUrl(it.image.imageUrl)
            nameDriverTextView.text = it.name
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.setView(this@DriverFeedbackFragment)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
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
                arguments?.getParcelable<DriverNearest>(BUNDLE_DRIVER)?.let { driver ->
                    arguments?.getInt(BUNDLE_ORDER_ID)?.let { orderId ->
                        presenter.feedbackDriver(
                            RateDriverRequest(
                                orderId,
                                feedbackEditText.text.toString(),
                                rating.toInt(),
                                driver.id,
                            )
                        )
                    }
                }
            }
        }
    }

    companion object {
        private const val BUNDLE_DRIVER = "BUNDLE_DRIVER"
        private const val BUNDLE_PARTNER = "BUNDLE_PARTNER"
        private const val BUNDLE_ORDER_ID = "BUNDLE_ORDER_ID"

        fun newInstance(
            driver: DriverNearest,
            orderId: Int,
            partner: Partner
        ): DriverFeedbackFragment {
            return DriverFeedbackFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(BUNDLE_DRIVER, driver)
                    putParcelable(BUNDLE_PARTNER, partner)
                    putInt(BUNDLE_ORDER_ID, orderId)
                }
            }
        }
    }
}
