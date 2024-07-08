package com.calcdistanceapp.data.local.converter

data class EntityConverters(
    val stationEntityToStationConverter: StationEntityToStationConverter,
    val stationKeywordEntityToStationKeywordConverter: StationKeywordEntityToStationKeywordConverter,
    val stationToEntityConverter: StationToStationEntityConverter,
    val stationKeywordToStationKeywordEntityConverter: StationKeywordToStationKeywordEntityConverter,
    val stringPolishAccentToStringNoAccentConverter: StringPolishAccentToStringNoAccentConverter
)