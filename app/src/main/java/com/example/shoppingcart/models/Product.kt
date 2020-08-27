package com.example.shoppingcart.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class Product (
    var title: String,
    var description: String,
    var image : Int,
    var price : BigDecimal,
    var sale_price : BigDecimal,
    var num_ratings : Int,
    var rating: BigDecimal,
    var serial_number : Int

) : Parcelable{

    fun hasSalePrice(): Boolean {
        val salePrice: Double = sale_price.toDouble()
        return salePrice > 0
    }

    fun getNumberRatingsString(): String? {
        return "($num_ratings)"
    }
}