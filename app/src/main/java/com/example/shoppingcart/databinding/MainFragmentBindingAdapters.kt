package com.example.shoppingcart.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcart.adapters.ProductsAdapter
import com.example.shoppingcart.models.Product

object MainFragmentBindingAdapters {
    private const val NUM_COLUMNS = 2
    @JvmStatic
    @BindingAdapter("productsList")
    fun setProductsList(view: RecyclerView, products: List<Product>) {
        if (products == null) {
            return
        }
        val layoutManager = view.layoutManager
        if (layoutManager == null) {
            view.layoutManager = GridLayoutManager(
                view.context,
                NUM_COLUMNS
            )
        }
        var adapter = view.adapter as ProductsAdapter?
        if (adapter == null) {
            adapter = ProductsAdapter(view.context, products.toMutableList())
            view.adapter = adapter
        }
    }

}