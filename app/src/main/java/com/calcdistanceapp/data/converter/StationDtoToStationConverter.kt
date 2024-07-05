package com.calcdistanceapp.data.converter

import com.calcdistanceapp.data.model.StationDto
import com.calcdistanceapp.domain.converter.Converter
import com.calcdistanceapp.domain.model.Station
import javax.inject.Inject

class StationDtoToStationConverter @Inject constructor() : Converter<StationDto, Station> {
    override fun convert(from: StationDto): Station =
        Station(
            name = from.name,
            latitude = from.latitude,
            longitude = from.longitude,
            hits = from.hits
        )
}
