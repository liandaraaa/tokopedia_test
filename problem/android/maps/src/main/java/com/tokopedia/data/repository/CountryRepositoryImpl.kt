package com.tokopedia.data.repository

import com.tokopedia.data.remote.ApiService
import com.tokopedia.domain.model.Country
import com.tokopedia.domain.repository.CountryRepository
import io.reactivex.Observable

class CountryRepositoryImpl() : CountryRepository {
    override fun getCountry(countryName: String): Observable<List<Country>> {
        val api = ApiService.create()
        return api.getCountry(countryName)
                .map {
                    it.map { item -> item.toCountry() }
                }
    }

    companion object{
        private var INSTANCE:CountryRepositoryImpl? = null
        fun getInstance() = INSTANCE ?: CountryRepositoryImpl().also {
            INSTANCE = it
        }
    }

}