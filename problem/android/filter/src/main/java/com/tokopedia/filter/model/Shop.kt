package com.tokopedia.filter.model

import com.tokopedia.core.emptyString

data class Shop(
        val city: String = emptyString(),
        val id: Int = 0,
        val name: String = emptyString()
)