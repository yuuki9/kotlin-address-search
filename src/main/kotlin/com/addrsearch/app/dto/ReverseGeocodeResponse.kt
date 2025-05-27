package com.addrsearch.app.dto

data class ReverseGeocodeResponse(
    val result: String,
    val code : List<String?>? = null,
    val x: Double,
    val y: Double
)
