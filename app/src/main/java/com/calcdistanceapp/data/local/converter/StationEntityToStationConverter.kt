package com.calcdistanceapp.data.local.converter

import com.calcdistanceapp.data.local.model.StationEntity
import com.calcdistanceapp.domain.converter.Converter
import com.calcdistanceapp.domain.model.Station
import javax.inject.Inject

class StationEntityToStationConverter @Inject constructor() : Converter<StationEntity, Station> {
    override fun convert(from: StationEntity): Station =
        Station(
            id = from.stationId,
            name = from.name,
            latitude = from.latitude,
            longitude = from.longitude,
            hits = from.hits
        )
}
