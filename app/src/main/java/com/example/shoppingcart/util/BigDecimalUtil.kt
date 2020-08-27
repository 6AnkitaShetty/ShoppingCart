package com.example.shoppingcart.util

import java.math.BigDecimal
import java.text.DecimalFormat

object BigDecimalUtil {

    @JvmStatic
    fun getValue(value: BigDecimal): String {

        val df = DecimalFormat("###,###,###.00")
        return (df.format(value)).toString()

    }
    @JvmStatic
    fun getFloat(value: BigDecimal): Float{
        return value.toFloat()
    }

}