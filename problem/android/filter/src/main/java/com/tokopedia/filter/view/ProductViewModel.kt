package com.tokopedia.filter.view

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tokopedia.core.Result
import com.tokopedia.filter.data.model.Product
import com.tokopedia.filter.data.repository.ProductRepositoryImpl

class ProductViewModel(private val context: Context) : ViewModel() {

    fun getProducts(): MutableLiveData<Result<List<Product>>> {
        val result = MutableLiveData<Result<List<Product>>>()
        result.postValue(Result.Loading())
        val data = ProductRepositoryImpl.getInstance(context).getProducts()
        if (data.isEmpty()) result.postValue(Result.Empty()) else
            result.postValue(Result.Success(data))
        return result
    }
}