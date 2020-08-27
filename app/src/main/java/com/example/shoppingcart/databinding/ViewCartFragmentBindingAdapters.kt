package com.example.shoppingcart.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcart.adapters.CartItemAdapter
import com.example.shoppingcart.models.CartItem

object ViewCartFragmentBindingAdapters {
    private const val TAG = "ViewCartFragmentBinding"

    @JvmStatic
    @BindingAdapter("cartItems")
    fun setCartItems(view: RecyclerView, cartItems: List<CartItem>
    ) {
        if (cartItems == null) {
            return
        }
        val layoutManager = view.layoutManager
        if (layoutManager == null) {
            view.layoutManager = (LinearLayoutManager(view.context))
        }
        var adapter = view.adapter as CartItemAdapter?
        if (adapter == null) {
            adapter = CartItemAdapter(view.context, cartItems.toMutableList())
            view.adapter = adapter
        } else {
            adapter.updateCartItems(cartItems)
        }
    }

}