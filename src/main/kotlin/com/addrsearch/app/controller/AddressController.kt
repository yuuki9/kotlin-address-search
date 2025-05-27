package com.addrsearch.app.controller

import com.addrsearch.app.dto.AddressSearchResponse
import com.addrsearch.app.dto.ReverseGeocodeResponse
import com.addrsearch.app.service.AddressService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/address")
@CrossOrigin(origins = ["*"]) // 모든 출처 허용 (필요에 따라 도메인 제한 가능)
class AddressController(
    private val addressService: AddressService
) {

    @GetMapping("/reverse-geocode")
    fun reverseGeocode(
        @RequestParam lon: Double,
        @RequestParam lat: Double
    ): ReverseGeocodeResponse = addressService.reverseGeocode(lon, lat)

    @GetMapping("/search")
    fun searchAddress(
        @RequestParam q: String
    ): List<AddressSearchResponse> = addressService.searchAddress(q)
}