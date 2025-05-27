package com.addrsearch.app.service

import com.addrsearch.app.dto.AddressSearchResponse
import com.addrsearch.app.dto.ReverseGeocodeResponse
import com.addrsearch.app.repository.AddressRepository
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.Geometry
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.io.WKTReader
import org.springframework.stereotype.Service

@Service
class AddressService(
    private val addressRepository: AddressRepository
) {
    fun reverseGeocode(lon: Double, lat: Double): ReverseGeocodeResponse {
        val doName =addressRepository.findRegionByCoordinatesDo(lon, lat)
        val si = addressRepository.findRegionByCoordinatesSi(lon, lat)
        val dong = addressRepository.findRegionByCoordinatesDong(lon, lat)
        val corridor = addressRepository.findCoordinatesWithCorridorCode()
        val vertiport = addressRepository.findCoordinatesWithVertiportCode()
        corridor.addAll(vertiport)
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

        return ReverseGeocodeResponse(result = "$doName ${si.first} ${dong.first}", x = lon, y = lat, code=includedCodes.toSet().toList())
    }

    fun searchAddress(query: String): List<AddressSearchResponse> {
        return addressRepository.searchAddressByKeyword(query)
    }


}