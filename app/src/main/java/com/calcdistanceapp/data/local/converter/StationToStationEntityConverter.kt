package com.calcdistanceapp.data.local.converter

import com.calcdistanceapp.data.local.model.StationEntity
import com.calcdistanceapp.domain.converter.Converter
import com.calcdistanceapp.domain.model.Station
import javax.inject.Inject

class StationToStationEntityConverter @Inject constructor() : Converter<Station, StationEntity> {
    override fun convert(from: Station): StationEntity =
        StationEntity(
            stationId = from.id,
            name = from.name,
            latitude = from.latitude,
            longitude = from.longitude,
            hits = from.hits
        )
}
