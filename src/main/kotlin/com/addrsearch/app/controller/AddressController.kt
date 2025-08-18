package com.addrsearch.app.controller

import com.addrsearch.app.dto.AddressSearchResponse
import com.addrsearch.app.dto.ReverseGeocodeResponse
import com.addrsearch.app.dto.SidoResponse
import com.addrsearch.app.dto.SigunguResponse
import com.addrsearch.app.service.GeoAddressSearchService
import com.addrsearch.app.service.KeywordAddressSearchService
import com.addrsearch.app.service.SidoListQueryService
import com.addrsearch.app.service.SigunguListQueryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/address")
class AddressController(
    private val geoAddressSearchService: GeoAddressSearchService,
    private val keywordAddressSearchService: KeywordAddressSearchService,
    private val sidoListQueryService: SidoListQueryService,
    private val sigunguListQueryService: SigunguListQueryService
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

    //시도 리스트
    @GetMapping("/sido")
    fun searchSido(): List<SidoResponse> = sidoListQueryService.getSidoList()

    //시도 값으로 읍면동 찾기
    @GetMapping("/sigungu")
    fun searchSido(@RequestParam id: Long): List<SigunguResponse> = sigunguListQueryService.execute(id)

}