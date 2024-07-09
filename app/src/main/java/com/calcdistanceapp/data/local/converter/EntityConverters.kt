package com.calcdistanceapp.data.local.converter

import com.calcdistanceapp.data.remote.converter.StationDtoToStationConverter
import com.calcdistanceapp.data.remote.converter.StationKeywordDtoToStationConverter

data class EntityConverters(
    val stationEntityToStationConverter: StationEntityToStationConverter,
    val stationKeywordEntityToStationKeywordConverter: StationKeywordEntityToStationKeywordConverter,
    val stationToEntityConverter: StationToStationEntityConverter,
    val stationKeywordToStationKeywordEntityConverter: StationKeywordToStationKeywordEntityConverter,
    val stringPolishAccentToStringNoAccentConverter: StringPolishAccentToStringNoAccentConverter,
    val stationDtoToStationConverter: StationDtoToStationConverter,
    val stationKeywordDtoToStationConverter: StationKeywordDtoToStationConverter
)