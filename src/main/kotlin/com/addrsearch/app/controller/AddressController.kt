package com.addrsearch.app.controller

import com.addrsearch.app.dto.AddressSearchResponse
import com.addrsearch.app.dto.ReverseGeocodeResponse
import com.addrsearch.app.service.GeoAddressSearchService
import com.addrsearch.app.service.KeywordAddressSearchService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/address")
class AddressController(
    private val geoAddressSearchService: GeoAddressSearchService,
    private val keywordAddressSearchService: KeywordAddressSearchService
) {

    @GetMapping("/location")
    fun getAddressFromCoordinates(
        @RequestParam lat: Double,
        @RequestParam lon: Double
    ): ReverseGeocodeResponse = geoAddressSearchService.execute(lon, lat)

    @GetMapping("/search")
    fun searchAddress(
        @RequestParam q: String
    ): List<AddressSearchResponse> = keywordAddressSearchService.execute(q)
}