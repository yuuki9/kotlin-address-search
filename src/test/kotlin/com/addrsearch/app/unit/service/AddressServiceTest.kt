package com.addrsearch.app.unit.service

import com.addrsearch.app.domain.Address
import com.addrsearch.app.domain.GeoPoint
import com.addrsearch.app.repository.AddressSearchRepository
import com.addrsearch.app.service.GeoAddressSearchServiceImpl
import com.addrsearch.app.service.KeywordAddressSearchServiceImpl
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AddressServiceTest {

    private val addressSearchRepository = mock<AddressSearchRepository>()
    private val keywordAddressSearchService = KeywordAddressSearchServiceImpl(addressSearchRepository)
    private val geoAddressSearchService = GeoAddressSearchServiceImpl(addressSearchRepository)


    @Test
    fun `키워드를 입력하여 주소 검색 수행 성공 테스트`() {
        // given
        val keyword = "서초대로"

        val mockResult = listOf(
            Address(
                id="1",
                sido = "서울특별시",
                sigungu = "서초구",
                road_name = "서초대로",
                dong = "방배동",
                building_name = null,
                full_road = "서울특별시 서초구 서초대로",
                buildingNumberSub = null,
                buildingNumberMain = null,
                zip_code = null,
                adminCode = null,
                location = GeoPoint(126.4593, 34.4593)
            ),
            Address(
                id="2",
                sido = "서울특별시",
                sigungu = "서초구",
                road_name = "서초대로",
                dong = "방배1동",
                building_name = null,
                buildingNumberSub = null,
                buildingNumberMain = null,
                full_road = "서울특별시 서초구 서초대로",
                zip_code = null,
                adminCode = null,
                location = GeoPoint(126.4593, 34.4593)
            )
        )
        whenever(addressSearchRepository.searchByKeyword(keyword)).thenReturn(mockResult)


        // When
        val result = keywordAddressSearchService.execute(keyword)

        // Then
        assertEquals(2, result.size)
        assertTrue(result[0].fullAddress.contains("서초대로"))
    }


    @Test
    fun `위치정보를 입력하여 근접한 주소 검색 성공 테스트`() {
        // Given
        val lon = 126.4593
        val lat = 34.4593

        val mockAddress =   Address(
            id="1",
            sido = "서울특별시",
            sigungu = "서초구",
            road_name = "서초대로",
            dong = "방배1동",
            building_name = null,
            buildingNumberSub = null,
            buildingNumberMain = null,
            zip_code = null,
            adminCode = null,
            location = GeoPoint(126.4593, 34.4593)
        )
        whenever(addressSearchRepository.searchNearestByCoordinate(lon, lat)).thenReturn(mockAddress)

        // When
        val result = geoAddressSearchService.execute(lon, lat)

        // Then
        assertEquals("서울특별시 서초구 방배1동", result.address)
        assertEquals(lon, result.x)
        assertEquals(lat, result.y)
    }
}