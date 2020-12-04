package com.tokopedia.filter.data.repository

import android.content.Context
import com.tokopedia.core.jsonToString
import com.tokopedia.filter.R
import com.tokopedia.filter.data.model.Product
import com.tokopedia.filter.data.model.Shop
import com.tokopedia.filter.domain.repository.ProductRepository
import org.json.JSONObject

class ProductRepositoryImpl(private val context: Context) : ProductRepository {
    override fun getProducts(): List<Product> {
        val data = mutableListOf<Product>()
        try {
            val fileContent = jsonToString(context, R.raw.products)
            val jObject = JSONObject(fileContent)
            val jObjectResult: JSONObject = jObject.getJSONObject("data")
            val jArray = jObjectResult.getJSONArray("products")
            for (i in 0 until jArray.length()) {
                val jShop = jArray.getJSONObject(i).getJSONObject("shop")
                data.add(
                        Product(
                                discountPercentage = jArray.getJSONObject(i).getInt("discountPercentage"),
                                id = jArray.getJSONObject(i).getInt("id"),
                                imageUrl = jArray.getJSONObject(i).getString("imageUrl"),
                                name = jArray.getJSONObject(i).getString("name"),
                                priceInt = jArray.getJSONObject(i).getInt("priceInt"),
                                slashedPriceInt = jArray.getJSONObject(i).getInt("slashedPriceInt"),
                                shop = Shop(
                                        city = jShop.getString("city"),
                                        id = jShop.getInt("id"),
                                        name = jShop.getString("name")
                                )
                        )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return data
    }

    companion object{
        private var INSTANCE:ProductRepositoryImpl? = null
        fun getInstance(context: Context) = INSTANCE ?: ProductRepositoryImpl(context).also {
            INSTANCE = it
        }
    }
}