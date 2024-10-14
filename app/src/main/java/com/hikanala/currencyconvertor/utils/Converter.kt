package com.hikanala.currencyconvertor.utils

object Converter {

    fun convert(amount:Int, amountRate: Double, targetRate: Double): Double{
        var convertedVal = 0.0
        convertedVal = amount * (amountRate / targetRate)
        return convertedVal
    }
}