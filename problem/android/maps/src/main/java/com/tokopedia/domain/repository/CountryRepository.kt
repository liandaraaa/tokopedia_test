package com.tokopedia.domain.repository

import com.tokopedia.domain.model.Country
import io.reactivex.Observable

interface CountryRepository {
    fun getCountry(countryName:String): Observable<List<Country>>
}