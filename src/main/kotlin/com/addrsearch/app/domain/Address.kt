package com.addrsearch.app.domain

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import org.springframework.data.elasticsearch.annotations.InnerField
import org.springframework.data.elasticsearch.annotations.MultiField
import org.springframework.data.elasticsearch.core.geo.GeoPoint

@Document(indexName = "korean_addresses_v4")
data class Address(

    @Id
    val id: String? = null,

    @MultiField(
        mainField = Field(type = FieldType.Text),
        otherFields = [InnerField(suffix = "keyword", type = FieldType.Keyword)]
    )
    val ogc_fid: String? = null,

    @MultiField(
        mainField = Field(type = FieldType.Text),
        otherFields = [InnerField(suffix = "keyword", type = FieldType.Keyword)]
    )
    val sig_cd: String? = null,

    @MultiField(
        mainField = Field(type = FieldType.Text),
        otherFields = [InnerField(suffix = "keyword", type = FieldType.Keyword)]
    )
    val emd_cd: String? = null,

    @MultiField(
        mainField = Field(type = FieldType.Text),
        otherFields = [InnerField(suffix = "keyword", type = FieldType.Keyword)]
    )
    val entrc_sq: String? = null,

    @MultiField(
        mainField = Field(type = FieldType.Text),
        otherFields = [InnerField(suffix = "keyword", type = FieldType.Keyword)]
    )
    val bdtyp_nm: String? = null,

    @MultiField(
        mainField = Field(type = FieldType.Text, analyzer = "autocomplete_index_analyzer", searchAnalyzer = "autocomplete_search_analyzer"),
        otherFields = [InnerField(suffix = "keyword", type = FieldType.Keyword)]
    )
    val sido: String? = null,

    @MultiField(
        mainField = Field(type = FieldType.Text, analyzer = "autocomplete_index_analyzer", searchAnalyzer = "autocomplete_search_analyzer"),
        otherFields = [InnerField(suffix = "keyword", type = FieldType.Keyword)]
    )
    val sigungu: String? = null,

    @Field(type = FieldType.Text, analyzer = "autocomplete_index_analyzer", searchAnalyzer = "autocomplete_search_analyzer")
    val dong: String? = null,

    @Field(name = "road_name", type = FieldType.Text, analyzer = "autocomplete_index_analyzer", searchAnalyzer = "autocomplete_search_analyzer")
    val road_name: String? = null, // 이름 변경

    @MultiField(
        mainField = Field(name = "building_number_main", type = FieldType.Text),
        otherFields = [InnerField(suffix = "keyword", type = FieldType.Keyword)]
    )
    val building_number_main: String? = null, // 이름 변경

    @MultiField(
        mainField = Field(name = "building_number_sub", type = FieldType.Text),
        otherFields = [InnerField(suffix = "keyword", type = FieldType.Keyword)]
    )
    val building_number_sub: String? = null, // 이름 변경

    @Field(type = FieldType.Text, analyzer = "autocomplete_index_analyzer", searchAnalyzer = "autocomplete_search_analyzer")
    val building_name: String? = null, // 이름 변경

    @Field(type = FieldType.Text, analyzer = "autocomplete_index_analyzer", searchAnalyzer = "autocomplete_search_analyzer")
    val full_address: String? = null, // 이름 변경

    @Field(type = FieldType.Text, analyzer = "autocomplete_index_analyzer", searchAnalyzer = "autocomplete_search_analyzer")
    val region: String? = null,

    @MultiField(
        mainField = Field(type = FieldType.Text),
        otherFields = [InnerField(suffix = "keyword", type = FieldType.Keyword)]
    )
    val post_num: String? = null,

    val location: com.addrsearch.app.domain.GeoPoint
)

data class GeoPoint( val lat: Double? = null, val lon: Double? = null )
