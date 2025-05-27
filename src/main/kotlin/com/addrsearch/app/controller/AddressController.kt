package com.addrsearch.app.controller

import com.addrsearch.app.dto.AddressSearchResponse
import com.addrsearch.app.dto.ReverseGeocodeResponse
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/address")
class AddressController(
) {

//    @GetMapping("/reverse-geocode")
//    fun reverseGeocode(
//        @RequestParam lon: Double,
//        @RequestParam lat: Double
//    ): ReverseGeocodeResponse = addressService.reverseGeocode(lon, lat)
//
//    @GetMapping("/search")
//    fun searchAddress(
//        @RequestParam q: String
//    ): List<AddressSearchResponse> = addressService.searchAddress(q)
}