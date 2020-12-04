package com.tokopedia.core

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

const val DEFAULT_ZOOM = 0

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun AppCompatActivity.showToast(message:String?){
    Toast.makeText(this, message ?: "Error tidak diketahui", Toast.LENGTH_LONG).show()
}

fun GoogleMap.addMarkLocation(
        latitude: Double,
        longitude: Double,
        title: String
): Marker {
    return this.addMarker(
            MarkerOptions().position(
                    LatLng(
                            latitude,
                            longitude
                    )
            ).title(title)
    )
}

fun GoogleMap.showLocation(latitude: Double, longitude: Double) {
    this.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                            latitude, longitude
                    ), DEFAULT_ZOOM.toFloat()
            )
    )
}
