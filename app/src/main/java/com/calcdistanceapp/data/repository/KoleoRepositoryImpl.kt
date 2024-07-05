package com.calcdistanceapp.data.repository

import com.calcdistanceapp.data.converter.StationDtoToStationConverter
import com.calcdistanceapp.data.converter.StationKeywordDtoToStationConverter
import com.calcdistanceapp.data.model.StationDto
import com.calcdistanceapp.data.model.StationKeywordDto
import com.calcdistanceapp.data.remote.api.KoleoApiService
import com.calcdistanceapp.domain.converter.Converter
import com.calcdistanceapp.domain.model.Station
import com.calcdistanceapp.domain.model.StationKeyword
import com.calcdistanceapp.domain.repository.KoleoRepository
import javax.inject.Inject

class KoleoRepositoryImpl @Inject constructor(
    private val apiService: KoleoApiService,
    private val stationDtoToStationConverter: StationDtoToStationConverter,
    private val stationKeywordDtoToStationKeywordConverter: StationKeywordDtoToStationConverter
) : KoleoRepository {

    override suspend fun getStations(): List<Station> {
        return apiService.getStations().map { stationDtoToStationConverter.convert(it) }
    }

    override suspend fun getStationKeywords(): List<StationKeyword> {
        return apiService.getStationKeywords()
            .map { stationKeywordDtoToStationKeywordConverter.convert(it) }
    }
}