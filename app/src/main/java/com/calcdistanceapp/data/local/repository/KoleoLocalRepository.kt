package com.calcdistanceapp.data.local.repository

import com.calcdistanceapp.data.local.model.SettingsEntity
import com.calcdistanceapp.domain.model.Station
import com.calcdistanceapp.domain.model.StationKeyword
import com.calcdistanceapp.domain.repository.KoleoRemoteRepository
import kotlinx.coroutines.flow.Flow


interface KoleoLocalRepository : KoleoRemoteRepository {
    suspend fun insertStations(stations: List<Station>)
    suspend fun insertStationKeywords(stationKeywords: List<StationKeyword>)
    suspend fun insertCreationDate()
    suspend fun deleteAllStations()
    suspend fun deleteAllStationKeywords()
    suspend fun deleteAllSettings()
    suspend fun getSettings(): SettingsEntity
    suspend fun getStartingStations(): List<Station>
    suspend fun getStartingStationKeywords(): List<StationKeyword>
    suspend fun searchStationsByKeyword(keyword: String): Flow<List<Station>>
}