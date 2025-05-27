package com.addrsearch.app.domain

data class Address(
    val sido: String,
    val sigungu: String,
    val dong: String,
    val roadName: String,
    val buildingNumberMain: String?,
    val buildingNumberSub: String?,
    val buildingName: String?,
    val zipCode: String?,
    val adminCode: String?,
    val location: GeoPoint
)

data class GeoPoint(val lat: Double, val lon: Double)