package com.tokopedia.filter.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tokopedia.core.jsonToString
import com.tokopedia.filter.R
import com.tokopedia.filter.adapter.ProductAdapter
import com.tokopedia.filter.model.jsonToProduct
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        showProductList()
    }

    private fun showProductList(){
        val fileContent = jsonToString(R.raw.products)
        val products = jsonToProduct(fileContent)

        val productAdapter = ProductAdapter(this, products)
        product_list.apply {
            layoutManager = GridLayoutManager(this@ProductActivity, 2)
            adapter = productAdapter
        }
    }
}