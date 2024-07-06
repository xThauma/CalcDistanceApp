package com.calcdistanceapp.data.local.converter

import com.calcdistanceapp.data.local.model.StationKeywordEntity
import com.calcdistanceapp.domain.converter.Converter
import com.calcdistanceapp.domain.model.StationKeyword
import javax.inject.Inject

class StationKeywordEntityToStationKeywordConverter @Inject constructor() : Converter<StationKeywordEntity, StationKeyword> {
    override fun convert(from: StationKeywordEntity): StationKeyword =
        StationKeyword(
            keyword = from.keyword,
            stationId = from.stationId
        )
}
