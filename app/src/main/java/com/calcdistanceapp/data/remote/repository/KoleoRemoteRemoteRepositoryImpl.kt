package com.calcdistanceapp.data.remote.repository

import com.calcdistanceapp.data.remote.api.KoleoApiService
import com.calcdistanceapp.data.remote.converter.StationDtoToStationConverter
import com.calcdistanceapp.data.remote.converter.StationKeywordDtoToStationConverter
import com.calcdistanceapp.domain.model.Station
import com.calcdistanceapp.domain.model.StationKeyword
import com.calcdistanceapp.domain.repository.KoleoRemoteRepository
import javax.inject.Inject

class KoleoRemoteRemoteRepositoryImpl @Inject constructor(
    private val apiService: KoleoApiService,
    private val stationDtoToStationConverter: StationDtoToStationConverter,
    private val stationKeywordDtoToStationKeywordConverter: StationKeywordDtoToStationConverter
) : KoleoRemoteRepository {

    override suspend fun getStations(): List<Station> {
        return apiService.getStations().map { stationDtoToStationConverter.convert(it) }
    }

    override suspend fun getStationKeywords(): List<StationKeyword> {
        return apiService.getStationKeywords()
            .map { stationKeywordDtoToStationKeywordConverter.convert(it) }
    }
}