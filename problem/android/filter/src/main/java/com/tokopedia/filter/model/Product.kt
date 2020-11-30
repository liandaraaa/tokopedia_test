package com.tokopedia.filter.model

import com.tokopedia.core.emptyString
import org.json.JSONObject

data class Product(
        val discountPercentage: Int = 0,
        val id: Int = 0,
        val imageUrl: String = emptyString(),
        val name: String = emptyString(),
        val priceInt: Int = 0,
        val shop: Shop = Shop(),
        val slashedPriceInt: Int = 0
)

fun jsonToProduct(fileContent:String):List<Product>{
    val data = mutableListOf<Product>()
    try {
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