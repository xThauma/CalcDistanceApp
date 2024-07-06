package com.calcdistanceapp.data.local.converter

import com.calcdistanceapp.data.local.model.StationKeywordEntity
import com.calcdistanceapp.domain.converter.Converter
import com.calcdistanceapp.domain.model.StationKeyword
import javax.inject.Inject

class StationKeywordToStationEntityConverter @Inject constructor() :
    Converter<StationKeyword, StationKeywordEntity> {
    override fun convert(from: StationKeyword): StationKeywordEntity =
        StationKeywordEntity(
            keyword = from.keyword,
            stationId = from.stationId
        )
}
