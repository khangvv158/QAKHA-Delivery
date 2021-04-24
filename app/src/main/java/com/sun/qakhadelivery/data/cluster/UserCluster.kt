package com.sun.qakhadelivery.data.cluster

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.sun.qakhadelivery.data.model.Image

data class UserCluster(
    val name: String = "",
    val latitude: Float = 0f,
    val longitude: Float = 0f,
    val image: Image?
) : ClusterItem {

    override fun getPosition(): LatLng {
        return LatLng(latitude.toDouble(), longitude.toDouble())
    }

    override fun getTitle(): String? {
        return null
    }

    override fun getSnippet(): String? {
        return null
    }
}
