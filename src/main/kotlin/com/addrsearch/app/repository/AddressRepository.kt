package com.addrsearch.app.repository

import com.addrsearch.app.dto.AddressSearchResponse
import com.addrsearch.app.dto.ReverseGeocodeResponse
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository


@Repository
class AddressRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    fun findRegionByCoordinatesDong(lon: Double, lat: Double): Pair<String, String> {
        val sql = """
        SELECT emd_kor_nm, ST_AsText(geom) AS geom
        FROM tl_scco_emd
        WHERE ST_Contains(geom, ST_SetSRID(ST_Point(?, ?), 4326))
    """.trimIndent()

        return jdbcTemplate.queryForObject(sql, arrayOf(lon, lat)) { rs, _ ->
            val name = rs.getString("emd_kor_nm")
            val geom = rs.getString("geom")
            Pair(name, geom)
        } ?: throw NoSuchElementException("위치 정보가 없습니다.")
    }

    fun findRegionByCoordinatesSi(lon: Double, lat: Double): Pair<String, String> {
        val sql = """
        SELECT sig_kor_nm, ST_AsText(geom) AS geom
        FROM tl_scco_sig
        WHERE ST_Contains(geom, ST_SetSRID(ST_Point(?, ?), 4326));
    """.trimIndent()

        return jdbcTemplate.queryForObject(sql, arrayOf(lon, lat)) { rs, _ ->
            val name = rs.getString("sig_kor_nm")
            val geom = rs.getString("geom")
            Pair(name, geom)
        } ?: throw NoSuchElementException("위치 정보가 없습니다.")
    }


    fun findRegionByCoordinatesDo(lon: Double, lat: Double): String {
        val sql = """
        SELECT *
        FROM tl_scco_ctprvn
        WHERE ST_Contains(geom, ST_SetSRID(ST_Point(?, ?), 4326));
    """.trimIndent()

        return jdbcTemplate.queryForObject(sql, arrayOf(lon, lat)) { rs, _ ->
            rs.getString("ctp_kor_nm")
        } ?: throw NoSuchElementException("위치 정보가 없습니다.")
    }


    fun searchAddressByKeyword(keyword: String): List<AddressSearchResponse> {
        val sql = """
        SELECT ctp_kor_nm, sig_nm, emd_mgr_nm, buld_nm, buld_mnnm, xcoord, ycoord
        FROM tl_spbd_etloc
        WHERE search_vector::text ILIKE ?
        LIMIT 20
    """.trimIndent()

        val param = "%$keyword%"

        return jdbcTemplate.query(sql, arrayOf(param)) { rs, _ ->
            val parts = listOf(
                rs.getString("ctp_kor_nm"),
                rs.getString("sig_nm"),
                rs.getString("emd_mgr_nm"),
                rs.getString("buld_nm"),
                rs.getString("buld_mnnm")
            ).filterNotNull().filter { it.isNotBlank() }

            AddressSearchResponse(
                fullAddress = parts.joinToString(" "),
                x = rs.getDouble("xcoord"),
                y = rs.getDouble("ycoord")
            )
        }
    }

    fun findCoordinatesWithCorridorCode(): MutableList<SkywaySegment> {
        val sql = """
        SELECT 
            c.cor_sq, 
            c.cor_id, 
            s.seg_ltt, 
            s.seg_lnt
        FROM 
            sdsp_corridor_info c
        JOIN 
            sdsp_corsegment_info s ON s.cor_sq = c.cor_sq
    """.trimIndent()

        return jdbcTemplate.query(sql) { rs, _ ->
            SkywaySegment(
                code = rs.getString("cor_id"),
                latitude = rs.getDouble("seg_ltt"),
                longitude = rs.getDouble("seg_lnt")
            )
        }
    }

    fun findCoordinatesWithVertiportCode(): MutableList<SkywaySegment> {
        val sql = """
        SELECT 
            c.vrt_id, 
            c.vrt_ltt, 
            c.vrt_lnt
        FROM 
            sdsp_vertiport_info c
    """.trimIndent()

        return jdbcTemplate.query(sql) { rs, _ ->
            SkywaySegment(
                code = rs.getString("vrt_id"),
                latitude = rs.getDouble("vrt_ltt"),
                longitude = rs.getDouble("vrt_lnt")
            )
        }
    }


}

data class SkywaySegment(
    val latitude: Double,
    val longitude: Double,
    val code: String
)