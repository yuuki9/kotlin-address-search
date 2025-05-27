package com.addrsearch.app.repository

import co.elastic.clients.elasticsearch._types.LatLonGeoLocation
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType
import com.addrsearch.app.domain.Address
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.client.elc.NativeQuery
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.stereotype.Repository
import org.elasticsearch.index.query.MultiMatchQueryBuilder.Type
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
        val query = NativeQuery.builder()
            .withQuery { q ->
                q.functionScore { fs ->
                    fs.query { fq ->
                        fq.bool { bq ->
                            bq.should { s ->
                                s.multiMatch { mm ->
                                    mm.query(keyword)
                                    mm.type(TextQueryType.BoolPrefix)
                                    mm.fields(
                                        "sido.text^5",
                                        "sigungu.text^4",
                                        "road_name^3",
                                        "building_name^2",
                                        "dong"
                                    )
                                }
                            }
                            bq.should { s ->
                                s.term { t ->
                                    t.field("sigungu")
                                    t.value(keyword)
                                    t.boost(10.0f)
                                }
                            }
                            bq.should { s ->
                                s.term { t ->
                                    t.field("dong")
                                    t.value(keyword)
                                    t.boost(9.0f)
                                }
                            }
                            bq.should { s ->
                                s.term { t ->
                                    t.field("building_name.keyword")
                                    t.value(keyword)
                                    t.boost(8.0f)
                                }
                            }
                        }
                    }
                }
            }
            .withPageable(PageRequest.of(0, 20)) // size: 20
            .build()
        val searchHits = elasticsearchOperations.search(query, Address::class.java)

        return searchHits.map { it.content }.toList()
    }
}
