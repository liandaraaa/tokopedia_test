package com.tokopedia.maps

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.tokopedia.core.*
import com.tokopedia.domain.model.Country
import kotlinx.android.synthetic.main.activity_maps.*

open class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mapFragment: SupportMapFragment? = null
    private var googleMap: GoogleMap? = null

    private lateinit var textCountryName: TextView
    private lateinit var textCountryCapital: TextView
    private lateinit var textCountryPopulation: TextView
    private lateinit var textCountryCallCode: TextView

    private var editText: EditText? = null
    private var buttonSubmit: View? = null

    private var countries: List<Country> = listOf()

    private lateinit var viewModel: MapsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        viewModel = MapsViewModel()
        bindViews()
        initListeners()
        loadMap()
    }

    private fun bindViews() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        editText = findViewById(R.id.editText)
        buttonSubmit = findViewById(R.id.buttonSubmit)
        textCountryName = findViewById(R.id.txtCountryName)
        textCountryCapital = findViewById(R.id.txtCountryCapital)
        textCountryPopulation = findViewById(R.id.txtCountryPopulation)
        textCountryCallCode = findViewById(R.id.txtCountryCallCode)
    }

    private fun initListeners() {
        buttonSubmit!!.setOnClickListener {
            viewModel.getCountry(editText?.text.toString()).observe(this, Observer {
                when (it) {
                    is Result.Loading -> {
                        pbCountry.visible()
                    }
                    is Result.Success -> {
                        pbCountry.gone()
                        reset()
                        countries = it.data
                        showCountry()
                    }
                    is Result.Error -> {
                        pbCountry.gone()
                        showToast(it.throwable.localizedMessage)
                    }
                }
            })
        }
    }

    private fun reset(){
        googleMap?.clear()
        txtCountryName.text = getString(R.string.label_country_name)
        txtCountryCapital.text =  getString(R.string.label_country_capital)
        txtCountryPopulation.text =  getString(R.string.label_country_population)
        txtCountryCallCode.text =  getString(R.string.label_country_call_code)
    }

    private fun loadMap() {
        mapFragment!!.getMapAsync { googleMap -> this@MapsActivity.googleMap = googleMap }
    }

    private fun showCountry() {
        countries.forEach { country ->
            googleMap?.addMarkLocation(
                    latitude = country.latLng.latitude,
                    longitude = country.latLng.longitude,
                    title = country.name
            )

            googleMap?.showLocation(latitude = country.latLng.latitude,
                    longitude = country.latLng.longitude)

            googleMap?.setOnMarkerClickListener { marker ->
                val selectedCountry = countries.find { it.latLng == marker.position }
                selectedCountry?.let { showCountryInformation(it) }
                return@setOnMarkerClickListener false
            }
        }
    }

    private fun showCountryInformation(country: Country) {
        country.apply {
            txtCountryName.text = String.format(getString(R.string.format_country_name, name))
            txtCountryCapital.text = String.format(getString(R.string.format_country_capital, capital))
            txtCountryPopulation.text = String.format(getString(R.string.format_country_population, population))
            txtCountryCallCode.text = String.format(getString(R.string.format_country_call_code, callingCode))
        }
    }

    override fun onMapReady(map: GoogleMap?) {

    }
}
