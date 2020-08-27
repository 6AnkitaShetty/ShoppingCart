package com.example.shoppingcart

import com.example.shoppingcart.models.CartItem
import com.example.shoppingcart.models.Product

interface IMainActivity {
    fun inflateViewProductFragment(product: Product)

    fun showQuantityDialog()

    fun setQuantity(quantity: Int)

    fun addToCart(product: Product?, quantity: Int)

    fun inflateViewCartFragment()

    fun setCartVisibility(visibility: Boolean)

    fun updateQuantity(product: Product?, quantity: Int)

    fun removeCartItem(cartItem: CartItem?)
}