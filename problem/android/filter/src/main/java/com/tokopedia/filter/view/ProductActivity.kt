package com.tokopedia.filter.view

import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.RangeSlider
import com.tokopedia.core.*
import com.tokopedia.filter.R
import com.tokopedia.filter.adapter.ProductAdapter
import com.tokopedia.filter.model.Product
import com.tokopedia.filter.model.jsonToProduct
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.layout_filter_bottomsheet_dialog.*
import kotlin.math.min

class ProductActivity : AppCompatActivity() {

    private var products: List<Product> = listOf()

    private val productAdapter: ProductAdapter by lazy { ProductAdapter(this, products) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        showProductList()

        fabFilter.setOnClickListener { showFilterDialog() }
    }

    private fun showProductList() {
        val fileContent = jsonToString(R.raw.products)
        products = jsonToProduct(fileContent)

        productAdapter.notifyDataAddOrUpdate(products)
        product_list.apply {
            layoutManager = GridLayoutManager(this@ProductActivity, 2)
            adapter = productAdapter
        }
    }

    private fun showFilterDialog() {
        generateBottomSheetDialog(
                layoutId = R.layout.layout_filter_bottomsheet_dialog,
                cancelable = true,
                style = R.style.AppBottomSheetDialogTheme
        ).apply {

            val locations = products.map { it.shop.city }.getMostCommonInList(2).map { it.key }

            locations.forEach { location ->
                val view = Chip(ContextThemeWrapper(this@ProductActivity, R.style.Widget_MaterialComponents_Chip_Choice))
                view.text = location
                view.isCheckable = true
                chipLocation.addView(view)
            }
            val otherLocation = Chip(ContextThemeWrapper(this@ProductActivity, R.style.Widget_MaterialComponents_Chip_Choice))
            otherLocation.text = getString(R.string.label_other)
            otherLocation.isCheckable = true
            chipLocation.addView(otherLocation)

            var location = emptyString()
            chipLocation.setOnCheckedChangeListener { group, checkedId ->
                location = group.findViewById<Chip>(checkedId)?.text.toString()
                if (location == getString(R.string.label_other)) location = emptyString()
            }

            val prices = products.map { it.priceInt.toFloat() }
            var maxPrice = prices.max() ?: 0f
            var minPrice = prices.min() ?: 0f
            sliderPrice.apply {
                values = arrayListOf(minPrice, maxPrice)
                valueFrom = minPrice
                valueTo = maxPrice
            }

            tvMaxPrice.text = toRupiahCurrency(maxPrice.toInt())
            tvMinPrice.text = toRupiahCurrency(minPrice.toInt())

            sliderPrice.setLabelFormatter { toRupiahCurrency(it.toInt()) }
            sliderPrice.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: RangeSlider) {
                    minPrice = slider.values[0]
                    maxPrice = slider.values[1]
                    tvMinPrice.text = toRupiahCurrency(minPrice.toInt())
                    tvMaxPrice.text = toRupiahCurrency(maxPrice.toInt())
                }

                override fun onStopTrackingTouch(slider: RangeSlider) {
                    minPrice = slider.values[0]
                    maxPrice = slider.values[1]
                    tvMinPrice.text = toRupiahCurrency(minPrice.toInt())
                    tvMaxPrice.text = toRupiahCurrency(maxPrice.toInt())
                }
            })

            btnClose.setOnClickListener { dismiss() }

            btnApply.setOnClickListener {
                dismiss()
                productAdapter.filterProduct(products, location, minPrice, maxPrice)
            }
        }
    }
}