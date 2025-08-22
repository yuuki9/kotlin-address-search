package com.addrsearch.app.repository

import co.elastic.clients.elasticsearch._types.DistanceUnit
import co.elastic.clients.elasticsearch._types.LatLonGeoLocation
import co.elastic.clients.elasticsearch._types.SortOrder
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType
import com.addrsearch.app.domain.Address
import com.addrsearch.app.dto.AddressResponse
import org.elasticsearch.common.geo.GeoPoint
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.client.elc.NativeQuery
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.stereotype.Repository
import org.elasticsearch.index.query.MultiMatchQueryBuilder.Type
import java.io.File

@Repository
class AddressSearchCustomImpl(
    private val elasticsearchOperations: ElasticsearchOperations
) : AddressSearchCustom {
    override fun searchNearestByCoordinate(lon: Double, lat: Double): Address? {
        val location = LatLonGeoLocation.of { loc ->
            loc.lat(lat).lon(lon)
        }

        val nativeQuery = NativeQuery.builder()
            .withQuery {
                it.geoDistance { geo ->
                    geo
                        .field("location")
                        .location { loc -> loc.latlon(location) }
                        .distance("5km")
                }
            }
            .withPageable(PageRequest.of(0, 1))
            .build()

        val results = elasticsearchOperations.search(nativeQuery, Address::class.java)
        return results.firstOrNull()?.content
    }

    override fun searchByKeyword(keyword: String): List<Address> {

        // 2. 기존 multiMatch 검색
        val query = NativeQuery.builder()
            .withQuery { q ->
                q.multiMatch { mm ->
                    mm.query(keyword)
                    mm.fields(
                        "sido^5",
                        "sigungu^4",
                        "dong^4",
                        "road_name^3",
                        "building_name^2",
                        "full_address"
                    )
                    mm.type(TextQueryType.BoolPrefix)
                }
            }
            .withPageable(PageRequest.of(0, 20))
            .build()

        val searchHits = elasticsearchOperations.search(query, Address::class.java)
            .map { it.content }
            .distinctBy { it.full_address }
            .toMutableList()

        // 3. 정확 일치가 있으면 최상단에 sido 값만 담은 객체 추가
//        exactHit?.let {
//            print(it.sido.toString())
//            val topAddress = it.copy(
//                sigungu = "",
//                dong = "",
//                road_name = "",
//                building_name = "",
//                full_address = it.sido
//            )
//            searchHits.add(0, topAddress)
//        }

        return searchHits
    }

    private fun readCsvAsMap(filePath: String): HashMap<String, String> {
        val map = hashMapOf<String, String>()
        File(filePath).forEachLine { line ->
            val parts = line.split(",")
            if (parts.size >= 2) {
                map[parts[0]] = parts[1] // 첫 번째 컬럼을 키, 두 번째를 값
            }
        }
        return map
    }
}
