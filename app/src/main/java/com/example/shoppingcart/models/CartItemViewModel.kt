package com.example.shoppingcart.models

import android.content.Context
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.shoppingcart.IMainActivity
import com.example.shoppingcart.BR

class CartItemViewModel : BaseObservable() {
    @get:Bindable
    var cartItem: CartItem? = null
        set(cartItem) {
            Log.d(TAG, "setQuantity: updating cart")
            field = cartItem
            notifyPropertyChanged(BR.cartItem)
        }

    fun increaseQuantity(context: Context) {
        val cartItem1 = cartItem
        cartItem1!!.quantity = (cartItem1.quantity + 1)
        cartItem = cartItem1
        val iMainActivity: IMainActivity = context as IMainActivity
        iMainActivity.updateQuantity(cartItem1.product, 1)
    }

    fun decreaseQuantity(context: Context) {
        val cartItem1 = cartItem
        val iMainActivity: IMainActivity = context as IMainActivity
        if (cartItem1!!.quantity > 1) {
            cartItem1.quantity = (cartItem1.quantity - 1)
            cartItem = cartItem1
            iMainActivity.updateQuantity(cartItem1.product, -1)
        } else if (cartItem1.quantity == 1) {
            cartItem1.quantity = (cartItem1.quantity - 1)
            cartItem = cartItem1
            iMainActivity.removeCartItem(cartItem1)
        }
    }

    fun getQuantityString(cartItem: CartItem): String {
        return "Qty: " + (cartItem.quantity).toString()
    }

    companion object {
        private const val TAG = "CartItemViewModel"
    }
}