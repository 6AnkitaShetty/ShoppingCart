package com.example.shoppingcart.util

import java.math.BigDecimal
import java.util.*

object Prices {
    var prices: HashMap<String, BigDecimal>? = null

    init {
        prices = HashMap()
        val products = Products()
        for (product in products.PRODUCTS) {
            prices!![(product.serial_number).toString()] = product.price
        }
    }
}
