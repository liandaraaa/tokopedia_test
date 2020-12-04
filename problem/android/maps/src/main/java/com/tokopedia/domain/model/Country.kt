package com.tokopedia.domain.model

import com.google.android.gms.maps.model.LatLng

data class Country(
    val name: String,
    val latLng: LatLng,
    val capital: String,
    val population: Int,
    val callingCode: String
)