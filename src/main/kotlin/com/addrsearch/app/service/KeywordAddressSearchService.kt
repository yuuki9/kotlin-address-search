package com.addrsearch.app.service

import com.addrsearch.app.domain.Address
import com.addrsearch.app.dto.AddressSearchResponse

interface KeywordAddressSearchService {
    fun execute(keyword: String): List<AddressSearchResponse>
}