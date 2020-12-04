package com.tokopedia.data.model


import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import com.tokopedia.domain.model.Country

data class CountryApiItem(
        @SerializedName("alpha2Code")
        val alpha2Code: String?,
        @SerializedName("alpha3Code")
        val alpha3Code: String?,
        @SerializedName("altSpellings")
        val altSpellings: List<String?>?,
        @SerializedName("area")
        val area: Double?,
        @SerializedName("borders")
        val borders: List<String?>?,
        @SerializedName("callingCodes")
        val callingCodes: List<String?>?,
        @SerializedName("capital")
        val capital: String?,
        @SerializedName("currencies")
        val currencies: List<String?>?,
        @SerializedName("demonym")
        val demonym: String?,
        @SerializedName("gini")
        val gini: Double?,
        @SerializedName("languages")
        val languages: List<String?>?,
        @SerializedName("latlng")
        val latlng: List<Double?>?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("nativeName")
        val nativeName: String?,
        @SerializedName("numericCode")
        val numericCode: String?,
        @SerializedName("population")
        val population: Int?,
        @SerializedName("region")
        val region: String?,
        @SerializedName("relevance")
        val relevance: String?,
        @SerializedName("subregion")
        val subregion: String?,
        @SerializedName("timezones")
        val timezones: List<String?>?,
        @SerializedName("topLevelDomain")
        val topLevelDomain: List<String?>?,
        @SerializedName("translations")
        val translations: Translations?
) {
    fun toCountry(): Country {
        return Country(
                name = name.orEmpty(),
                latLng = LatLng(latlng?.get(0) ?: 0.0, latlng?.get(1) ?: 0.0),
                population = population ?: 0,
                capital = capital.orEmpty(),
                callingCode = callingCodes?.get(0).orEmpty()
        )
    }
}

data class Translations(
        @SerializedName("de")
        val de: String?,
        @SerializedName("es")
        val es: String?,
        @SerializedName("fr")
        val fr: String?,
        @SerializedName("it")
        val `it`: String?,
        @SerializedName("ja")
        val ja: String?
)