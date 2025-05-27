package com.addrsearch.app.service

import com.addrsearch.app.dto.AddressSearchResponse
import com.addrsearch.app.repository.AddressSearchRepository

class KeywordAddressSearchServiceImpl(
    val addressSearchRepository: AddressSearchRepository
) : KeywordAddressSearchService {
    override fun execute(keyword: String): List<AddressSearchResponse> {
        return addressSearchRepository.searchByKeyword(keyword).map {
            AddressSearchResponse(
                fullAddress = "${it.sido} ${it.sigungu} ${it.roadName} ${it.dong}",
                x = it.location.lon,
                y = it.location.lat
            )
        }
    }
}