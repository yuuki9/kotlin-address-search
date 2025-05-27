package com.addrsearch.app.repository

import com.addrsearch.app.domain.Address
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface AddressSearchRepository : ElasticsearchRepository<Address, String> {
    fun searchByKeyword(keyword: String): List<Address>
    fun searchNearestByCoordinate(lon : Double, lat : Double): Address
}