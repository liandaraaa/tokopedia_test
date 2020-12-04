package com.tokopedia.maps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tokopedia.core.Result
import com.tokopedia.data.repository.CountryRepositoryImpl
import com.tokopedia.domain.model.Country
import com.tokopedia.domain.repository.CountryRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MapsViewModel() : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getCountry(countryName: String): MutableLiveData<Result<List<Country>>> {
        val result = MutableLiveData<Result<List<Country>>>()
        val disposable = CountryRepositoryImpl.getInstance().getCountry(countryName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    result.postValue(Result.Loading())
                }
                .subscribe({ data ->
                    if (data.isEmpty()) result.postValue(Result.Empty()) else
                        result.postValue(Result.Success(data))
                }, {
                    result.postValue(Result.Error(it))
                })
        compositeDisposable.add(disposable)
        return result
    }

    private fun clearDisposable() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        clearDisposable()
    }
}