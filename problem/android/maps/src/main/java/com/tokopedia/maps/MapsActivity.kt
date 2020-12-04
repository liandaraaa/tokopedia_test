package com.tokopedia.maps

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.tokopedia.core.*
import com.tokopedia.domain.model.Country
import kotlinx.android.synthetic.main.activity_maps.*

open class MapsActivity : AppCompatActivity() {

    private var mapFragment: SupportMapFragment? = null
    private var googleMap: GoogleMap? = null

    private lateinit var textCountryName: TextView
    private lateinit var textCountryCapital: TextView
    private lateinit var textCountryPopulation: TextView
    private lateinit var textCountryCallCode: TextView

    private var editText: EditText? = null
    private var buttonSubmit: View? = null

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
                    is Result.Loading ->{
                        pbCountry.visible()
                    }
                    is Result.Success -> {
                        pbCountry.gone()
                        val country = it.data
                        showCountryInformation(country.first())
                    }
                    is Result.Error -> {
                        pbCountry.gone()
                        showToast(it.throwable.message)
                    }
                }
            })
            // search by the given country name, and
            // 1. pin point to the map
        }
    }

    private fun loadMap() {
        mapFragment!!.getMapAsync { googleMap -> this@MapsActivity.googleMap = googleMap }
    }

    private fun showCountryInformation(country:Country){
        country.apply {
            txtCountryName.text = String.format(getString(R.string.format_country_name, name))
            txtCountryCapital.text = String.format(getString(R.string.format_country_capital, capital))
            txtCountryPopulation.text = String.format(getString(R.string.format_country_population, population))
            txtCountryCallCode.text = String.format(getString(R.string.format_country_call_code, callingCode))
        }
    }
}
