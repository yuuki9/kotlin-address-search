package com.addrsearch.app.service

import com.addrsearch.app.domain.Address
import com.addrsearch.app.dto.ReverseGeocodeResponse

interface GeoAddressSearchService {
    fun execute(lon: Double, lat: Double): ReverseGeocodeResponse
}