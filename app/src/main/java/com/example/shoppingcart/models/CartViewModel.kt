package com.example.shoppingcart.models

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.shoppingcart.util.BigDecimalUtil
import com.example.shoppingcart.util.Prices
import java.math.BigDecimal
import java.util.*
import com.example.shoppingcart.BR

class CartViewModel : BaseObservable() {
    private var cart: List<CartItem> = ArrayList()

    @get:Bindable
    var isCartVisible = false
        set(cartVisible) {
            field = cartVisible
            notifyPropertyChanged(BR.cartVisible)
        }

    @Bindable
    fun getCart(): List<CartItem> {
        return cart
    }

    fun setCart(cart: List<CartItem>) {
        Log.d(TAG, "setCart: updating cart.")
        this.cart = cart
        notifyPropertyChanged(BR.cart)
    }

    val productQuantitiesString: String
        get() {
            var totalItems = 0
            for (cartItem in cart) {
                totalItems += cartItem.quantity
            }
            var s = ""
            s = if (totalItems > 1) {
                "items"
            } else {
                "item"
            }
            return "( $totalItems $s): "
        }

    val totalCostString: String
        get() {
            var totalCost = 0.0
            for (cartItem in cart) {
                val productQuantity: Int = cartItem.quantity
                val cost: Double = productQuantity * Prices.prices?.get((cartItem.product.serial_number).toString())!!.toDouble()
                totalCost += cost
            }
            return "$" + BigDecimalUtil.getValue(BigDecimal(totalCost))
        }

    companion object {
        private const val TAG = "CartViewModel"
    }
}