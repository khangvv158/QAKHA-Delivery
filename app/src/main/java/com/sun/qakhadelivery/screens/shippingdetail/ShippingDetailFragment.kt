package com.sun.qakhadelivery.screens.shippingdetail

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.clustering.ClusterManager
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.cluster.UserCluster
import com.sun.qakhadelivery.data.cluster.UserRenderer
import com.sun.qakhadelivery.data.model.DriverFirebase
import com.sun.qakhadelivery.data.repository.DriverFirebaseRepositoryImpl
import com.sun.qakhadelivery.data.repository.OrderFirebaseRepositoryImpl
import com.sun.qakhadelivery.data.source.remote.schema.response.OrderResponse
import com.sun.qakhadelivery.extensions.makeText
import com.sun.qakhadelivery.utils.GoogleMapHelper
import com.sun.qakhadelivery.utils.LatLngInterpolator
import com.sun.qakhadelivery.utils.MarkerAnimationHelper
import kotlinx.android.synthetic.main.bottom_sheet_shipping_detail.*
import kotlinx.android.synthetic.main.fragment_shipping_detail.*

class ShippingDetailFragment : Fragment(), ShippingDetailContract.View {

    private lateinit var googleMap: GoogleMap
    private lateinit var orderResponse: OrderResponse
    private lateinit var notificationManager: NotificationManager
    private lateinit var userClusterManager: ClusterManager<UserCluster>
    private lateinit var userRenderer: UserRenderer
    private val presenter by lazy {
        ShippingDetailPresenter(
            OrderFirebaseRepositoryImpl.getInstance(),
            DriverFirebaseRepositoryImpl.getInstance()
        )
    }
    private val googleMapHelper by lazy {
        GoogleMapHelper()
    }
    private var animateCameraFirst = true
    private var markerDriver: Marker? = null
    private var markerPartner: Marker? = null
    private var onOrderDone: OnOrderDone? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getParcelable<OrderResponse>(BUNDLE_ORDER_RESPONSE)?.let {
            orderResponse = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shipping_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        presenter.setView(this)
        MapsInitializer.initialize(requireContext())
        mapFragment?.getMapAsync {
            setUpGoogleMap(it)
            initViewsBottomSheet()
        }
    }

    override fun isOrderShippingByDriverSuccess() {
        presenter.fetchLocationByDriver(orderResponse.order.driver_id)
    }

    override fun isOrderShippingByDriverDone() {
        googleMap.clear()
        userClusterManager.clearItems()
        createNotificationChannelOrder()
        notificationManager.notify(NOTIFICATION_ORDER_ID, createNotificationOrderDone())
        onOrderDone?.onOrderDone()
        parentFragmentManager.popBackStack()
    }

    override fun fetchLocationByDriverSuccess(driverFirebase: DriverFirebase) {
        driverFirebase.apply {
            if (animateCameraFirst) {
                googleMap.animateCamera(
                    googleMapHelper.buildCameraUpdate(
                        LatLng(
                            latitude.toDouble(),
                            longitude.toDouble()
                        )
                    )
                )
                animateCameraFirst = false
            }
            if (markerDriver == null) {
                markerDriver = googleMap.addMarker(
                    googleMapHelper.getDriverMarkerOptions(
                        LatLng(
                            latitude.toDouble(),
                            longitude.toDouble()
                        )
                    )
                )
            } else {
                markerDriver?.let {
                    MarkerAnimationHelper.animateMarkerToGB(
                        it, LatLng(
                            latitude.toDouble(),
                            longitude.toDouble()
                        ), LatLngInterpolator.Spherical()
                    )
                }
            }
        }
    }

    override fun onError(message: String) {
        makeText(message)
    }

    @SuppressLint("MissingPermission")
    private fun setUpGoogleMap(map: GoogleMap) {
        googleMap = map
        initCluster()
        isOrderShipping()
        handleEvents()
    }

    private fun initCluster() {
        userClusterManager = ClusterManager(requireContext(), googleMap)
        userRenderer = UserRenderer(requireContext(), googleMap, userClusterManager)
        userClusterManager.renderer = userRenderer
        userClusterManager.addItem(
            UserCluster(
                orderResponse.order.name,
                orderResponse.userGPS.latitude,
                orderResponse.userGPS.longitude,
                orderResponse.order.image
            )
        )
        if (markerPartner == null) {
            markerPartner = googleMap.addMarker(
                googleMapHelper.getPartnerMarkerOptions(
                    LatLng(
                        orderResponse.partner.latitude.toDouble(),
                        orderResponse.partner.longitude.toDouble()
                    )
                )
            )
        }
    }

    fun registerOnOrderDone(onOrderDone: OnOrderDone) {
        this.onOrderDone = onOrderDone
    }

    private fun isOrderShipping() {
        orderResponse.apply {
            presenter.isOrderShippingByDriver(order.id, order.driver_id)
        }
    }

    private fun handleEvents() {
        callImageView.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:${orderResponse.driverNearest.phone_number}")
                )
            )
        }
        imageViewBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        callImageView.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:${orderResponse.driverNearest.phone_number}")
                )
            )
        }
    }


    private fun initViewsBottomSheet() {
        Glide.with(requireContext()).load(orderResponse.driverNearest.image.imageUrl)
            .into(imageViewAvatarDriver)
        textViewNameDriver.text = orderResponse.driverNearest.name
        textViewLicensePlateDriver.text = orderResponse.driverNearest.license_plate
        addressTextView.text = orderResponse.order.address
        userNameTextView.text = orderResponse.order.name
        textViewDateOrder.text = orderResponse.order.delivery_time
        phoneNumberTextView.text = orderResponse.order.phone_number
        textViewNamePartner.text = orderResponse.partner.name
        textViewPriceSubtotal.text = orderResponse.order.subtotal.toString()
        textViewPriceShippingFee.text = orderResponse.order.shipping_fee.toString()
        textViewPriceDiscount.text = orderResponse.order.discount.toString()
        textViewPriceTotal.text = orderResponse.order.total.toString()
    }

    private fun createNotificationOrderDone(): Notification {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(requireContext(), CHANNEL_ORDER_ID)
                .setContentTitle(getString(R.string.complete_delivery))
                .setContentText(getString(R.string.the_order_has_been_successfully_delivered))
                .setSmallIcon(R.drawable.ic_box)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_delivery_box))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true)
                .build()
        } else {
            NotificationCompat.Builder(requireContext())
                .setContentTitle(getString(R.string.complete_delivery))
                .setContentText(getString(R.string.the_order_has_been_successfully_delivered))
                .setSmallIcon(R.drawable.ic_box)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_delivery_box))
                .setAutoCancel(true)
                .build()
        }
    }

    private fun createNotificationChannelOrder() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ORDER_ID,
                getString(R.string.notification_order_chanel),
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager =
                activity?.getSystemService(NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannel(serviceChannel)
        }
    }

    companion object {

        const val NOTIFICATION_ORDER_ID = 2
        const val CHANNEL_ORDER_ID = "CHANNEL_ORDER_ID"
        private const val BUNDLE_ORDER_RESPONSE = "BUNDLE_ORDER_RESPONSE"

        fun newInstance(orderResponse: OrderResponse) = ShippingDetailFragment().apply {
            arguments = bundleOf(BUNDLE_ORDER_RESPONSE to orderResponse)
        }
    }
}
