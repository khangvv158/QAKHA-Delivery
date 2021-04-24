package com.sun.qakhadelivery.utils

import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sun.qakhadelivery.R

class GoogleMapHelper {

    fun getPartnerMarkerOptions(position: LatLng) : MarkerOptions {
        return getMarkerOptions(R.drawable.ic_marker_store,position)
    }

    fun buildCameraUpdate(latLng: LatLng): CameraUpdate {
        val cameraPosition = CameraPosition.Builder()
            .target(latLng)
            .tilt(TILT_LEVEL.toFloat())
            .zoom(ZOOM_LEVEL.toFloat())
            .build()
        return CameraUpdateFactory.newCameraPosition(cameraPosition)
    }

    fun getNormalMarkerOptions(position: LatLng): MarkerOptions {
        return MarkerOptions().position(position)
    }

    fun getDriverMarkerOptions(position: LatLng): MarkerOptions {
        val options = getMarkerOptions(R.drawable.delivery_man, position)
        options.flat(true)
        return options
    }

    private fun getMarkerOptions(resource: Int, position: LatLng): MarkerOptions {
        return MarkerOptions()
            .icon(BitmapDescriptorFactory.fromResource(resource))
            .position(position)
    }

    companion object {
        private const val ZOOM_LEVEL = 18
        private const val TILT_LEVEL = 25
    }
}
