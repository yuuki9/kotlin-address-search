package com.addrsearch.app.service

import com.addrsearch.app.dto.AddressSearchResponse
import com.addrsearch.app.repository.AddressSearchRepository
import org.springframework.stereotype.Service

@Service
class KeywordAddressSearchServiceImpl(
    val addressSearchRepository: AddressSearchRepository
) : KeywordAddressSearchService {
    override fun execute(keyword: String): List<AddressSearchResponse> {
        return addressSearchRepository.searchByKeyword(keyword)
            .filter { it.full_road != null }
            .map {
                AddressSearchResponse(
                    fullAddress = it.full_road!!,
                    x = it.location.lon,
                    y = it.location.lat
                )
            }
    }
}