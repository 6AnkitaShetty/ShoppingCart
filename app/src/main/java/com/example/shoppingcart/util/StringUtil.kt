package com.example.shoppingcart.util

object StringUtil {
    @JvmStatic
    fun getQuantityString(quantity: Int): String {
        return "Qty: $quantity"
    }
    @JvmStatic
    fun convertIntToString(value: Int): String {
        return value.toString()
    }
}