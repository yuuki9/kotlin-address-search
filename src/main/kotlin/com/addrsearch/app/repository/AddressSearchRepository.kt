package com.addrsearch.app.repository

import com.addrsearch.app.domain.Address
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressSearchRepository : ElasticsearchRepository<Address, String> , AddressSearchCustom {
    override fun searchByKeyword(keyword: String): List<Address>
    override fun searchNearestByCoordinate(lon: Double, lat: Double): Address?
}