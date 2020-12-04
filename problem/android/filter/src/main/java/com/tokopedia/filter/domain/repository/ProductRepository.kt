package com.tokopedia.filter.domain.repository

import com.tokopedia.filter.data.model.Product

interface ProductRepository {
    fun getProducts():List<Product>
}