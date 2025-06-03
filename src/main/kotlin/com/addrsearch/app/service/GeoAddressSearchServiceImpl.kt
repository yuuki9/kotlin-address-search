package com.addrsearch.app.service


import com.addrsearch.app.dto.ReverseGeocodeResponse
import com.addrsearch.app.repository.AddressRepository
import com.addrsearch.app.repository.AddressSearchRepository
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.Geometry
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.io.WKTReader
import org.springframework.stereotype.Service

@Service
class GeoAddressSearchServiceImpl(
    private val addressSearchRepository: AddressSearchRepository,
    private val addressRepository: AddressRepository
) : GeoAddressSearchService {
    override fun execute(lon: Double, lat: Double): ReverseGeocodeResponse {
        val si = addressRepository.findRegionByCoordinatesSi(lon, lat)
        val corridor = addressRepository.findCoordinatesWithCorridorCode()
        val vertiport = addressRepository.findCoordinatesWithVertiportCode()
        corridor.addAll(vertiport)
        val result = addressSearchRepository.searchNearestByCoordinate(lon, lat)

        val wkt = si.second
        val wktReader = WKTReader()
        val regionGeom: Geometry = wktReader.read(wkt)

        val geometryFactory = GeometryFactory()
        val includedCodes = mutableListOf<String>()

        for (segment in corridor) {  // segments는 SkywaySegment 리스트
            val point: Point = geometryFactory.createPoint(Coordinate(segment.longitude, segment.latitude))
            if (regionGeom.contains(point)) {
                includedCodes.add(segment.code)
            }
        }

        return ReverseGeocodeResponse(
            address = "${result!!.sido} ${result.sigungu} ${result.dong}",
            code = includedCodes.toSet().toList(),
            x = lon,
            y = lat
        )
    }
}