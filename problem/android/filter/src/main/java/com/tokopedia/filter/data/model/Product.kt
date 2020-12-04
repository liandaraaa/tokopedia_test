package com.tokopedia.filter.data.model

import com.tokopedia.core.emptyString

data class Product(
        val discountPercentage: Int = 0,
        val id: Int = 0,
        val imageUrl: String = emptyString(),
        val name: String = emptyString(),
        val priceInt: Int = 0,
        val shop: Shop = Shop(),
        val slashedPriceInt: Int = 0
)