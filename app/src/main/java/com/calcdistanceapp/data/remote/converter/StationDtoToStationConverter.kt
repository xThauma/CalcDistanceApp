package com.calcdistanceapp.data.remote.converter

import com.calcdistanceapp.data.remote.model.StationDto
import com.calcdistanceapp.domain.converter.Converter
import com.calcdistanceapp.domain.model.Station
import javax.inject.Inject

class StationDtoToStationConverter @Inject constructor() : Converter<StationDto, Station> {
    override fun convert(from: StationDto): Station =
        Station(
            id = from.id,
            name = from.name,
            latitude = from.latitude,
            longitude = from.longitude,
            hits = from.hits,
            city = from.city,
            region = from.region,
            country = from.country,
            isGroup = from.isGroup
        )
}
