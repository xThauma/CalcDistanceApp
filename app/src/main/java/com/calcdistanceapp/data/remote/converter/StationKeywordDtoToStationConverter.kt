package com.calcdistanceapp.data.remote.converter

import com.calcdistanceapp.data.remote.model.StationKeywordDto
import com.calcdistanceapp.domain.converter.Converter
import com.calcdistanceapp.domain.model.StationKeyword
import javax.inject.Inject

class StationKeywordDtoToStationConverter @Inject constructor() :
    Converter<StationKeywordDto, StationKeyword> {
    override fun convert(from: StationKeywordDto): StationKeyword =
        StationKeyword(
            keyword = from.keyword,
            stationId = from.stationId
        )
}
