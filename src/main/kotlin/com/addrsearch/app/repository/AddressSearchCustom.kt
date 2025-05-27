package com.addrsearch.app.repository

import com.addrsearch.app.domain.Address

interface AddressSearchCustom {
    fun searchNearestByCoordinate(lon: Double, lat: Double): Address?
    fun searchByKeyword(keyword: String): List<Address>
}