package com.addrsearch.app.service


import com.addrsearch.app.dto.ReverseGeocodeResponse
import com.addrsearch.app.repository.AddressSearchRepository
import org.springframework.stereotype.Service

@Service
class GeoAddressSearchServiceImpl(
    private val addressSearchRepository: AddressSearchRepository
) : GeoAddressSearchService {
    override fun execute(lon: Double, lat: Double): ReverseGeocodeResponse {
        val result = addressSearchRepository.searchNearestByCoordinate(lon, lat)
        return ReverseGeocodeResponse(
            address = "${result!!.sido} ${result.sigungu} ${result.dong}",
            code = null,
            x = lon,
            y = lat
        )
    }
}