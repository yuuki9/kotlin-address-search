package com.addrsearch.app.domain

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import org.springframework.data.elasticsearch.annotations.InnerField
import org.springframework.data.elasticsearch.annotations.MultiField

@Document(indexName = "korean_addresses_v3")
data class Address(
    @Id
    val id: String,

    // sido, sigungu, dong은 keyword + text(nori_analyzer)
    @MultiField(
        mainField = Field(type = FieldType.Keyword, normalizer = "lowercase_normalizer"),
        otherFields = [
            InnerField(suffix = "text", type = FieldType.Text, analyzer = "nori_analyzer")
        ]
    )
    val sido: String,

    @MultiField(
        mainField = Field(type = FieldType.Keyword, normalizer = "lowercase_normalizer"),
        otherFields = [
            InnerField(suffix = "text", type = FieldType.Text, analyzer = "nori_analyzer")
        ]
    )
    val sigungu: String,

    @MultiField(
        mainField = Field(type = FieldType.Keyword, normalizer = "lowercase_normalizer"),
        otherFields = [
            InnerField(suffix = "text", type = FieldType.Text, analyzer = "nori_analyzer")
        ]
    )
    val dong: String,

    // edge_ngram + ngram + keyword
    @MultiField(
        mainField = Field(type = FieldType.Text, analyzer = "edge_ngram_analyzer"),
        otherFields = [
            InnerField(suffix = "ngram", type = FieldType.Text, analyzer = "ngram_analyzer"),
            InnerField(suffix = "keyword", type = FieldType.Keyword)
        ]
    )
    val road_name: String? = null,

    // building number는 그대로 keyword로 둠
    @Field(name = "building_number_main", type = FieldType.Keyword)
    val buildingNumberMain: String? = null,

    @Field(name = "building_number_sub", type = FieldType.Keyword)
    val buildingNumberSub: String? = null,

    @MultiField(
        mainField = Field(type = FieldType.Text, analyzer = "edge_ngram_analyzer"),
        otherFields = [
            InnerField(suffix = "ngram", type = FieldType.Text, analyzer = "ngram_analyzer"),
            InnerField(suffix = "keyword", type = FieldType.Keyword)
        ]
    )
    val building_name: String? = null,

    @Field(type = FieldType.Keyword)
    val zip_code: String? = null,

    @Field(type = FieldType.Keyword)
    val adminCode: String? = null,

    @MultiField(
        mainField = Field(type = FieldType.Text, analyzer = "edge_ngram_analyzer"),
        otherFields = [
            InnerField(suffix = "ngram", type = FieldType.Text, analyzer = "ngram_analyzer"),
            InnerField(suffix = "keyword", type = FieldType.Keyword)
        ]
    )
    val full_road: String? = null,

    val location: GeoPoint
)

data class GeoPoint(
    val lat: Double,
    val lon: Double
)