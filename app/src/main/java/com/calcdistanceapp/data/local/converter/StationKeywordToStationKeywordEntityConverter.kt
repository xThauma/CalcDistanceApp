package com.calcdistanceapp.data.local.converter

import com.calcdistanceapp.data.local.model.StationKeywordEntity
import com.calcdistanceapp.domain.converter.Converter
import com.calcdistanceapp.domain.model.StationKeyword
import javax.inject.Inject

class StationKeywordToStationKeywordEntityConverter @Inject constructor(
    private val stringPolishAccentToStringNoAccentConverter: StringPolishAccentToStringNoAccentConverter
) : Converter<StationKeyword, StationKeywordEntity> {
    override fun convert(from: StationKeyword): StationKeywordEntity =
        StationKeywordEntity(
            keyword = stringPolishAccentToStringNoAccentConverter.convert(from.keyword),
            stationId = from.stationId
        )
}
