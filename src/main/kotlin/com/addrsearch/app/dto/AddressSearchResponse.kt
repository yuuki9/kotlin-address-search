package com.addrsearch.app.dto

data class AddressSearchResponse(
    val fullAddress: String,
    val buildingName : String? = null,
    val x : Double,
    val y : Double
)