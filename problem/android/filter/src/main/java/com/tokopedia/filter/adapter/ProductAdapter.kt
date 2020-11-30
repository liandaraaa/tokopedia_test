package com.tokopedia.filter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tokopedia.core.makeStrikeThrough
import com.tokopedia.core.toPercentageFormat
import com.tokopedia.core.toRupiahCurrency
import com.tokopedia.filter.R
import com.tokopedia.filter.model.Product
import kotlinx.android.synthetic.main.product_item.view.*

class ProductAdapter(val context:Context, val data:List<Product>) :RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){

    inner class ProductViewHolder(view:View):RecyclerView.ViewHolder(view){
        fun bind(product:Product){
            with(itemView){

                tvSecondPriceProduct.visibility = if (product.discountPercentage == 0) View.GONE else View.VISIBLE
                tvDiscount.visibility = if (product.discountPercentage == 0) View.GONE else View.VISIBLE

                Glide.with(context).load(product.imageUrl).into(ivProduct)
                tvProductName.text = product.name
                tvSellerName.text = product.shop.name
                tvCity.text = product.shop.city
                tvPriceProduct.text = toRupiahCurrency(product.priceInt)
                tvSecondPriceProduct.text = toRupiahCurrency(product.slashedPriceInt)
                tvDiscount.text = toPercentageFormat(product.discountPercentage)

                tvSecondPriceProduct.makeStrikeThrough()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.product_item, parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}