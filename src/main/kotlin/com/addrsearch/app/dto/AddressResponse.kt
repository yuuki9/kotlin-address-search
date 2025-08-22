package com.addrsearch.app.dto

data class AddressResponse(
    val full_address: String?,   // ES 필드명과 정확히 일치
    val road_name: String?,
    val dong: String?,
    val region: String?,
    val building_name: String?
)