package com.calcdistanceapp.data.local.repository

import com.calcdistanceapp.data.local.converter.EntityConverters
import com.calcdistanceapp.data.local.dao.SettingsDao
import com.calcdistanceapp.data.local.dao.StationDao
import com.calcdistanceapp.data.local.dao.StationKeywordDao
import com.calcdistanceapp.data.local.model.SettingsEntity
import com.calcdistanceapp.domain.model.Station
import com.calcdistanceapp.domain.model.StationKeyword
import javax.inject.Inject

class KoleoLocalRepositoryImpl @Inject constructor(
    private val stationDao: StationDao,
    private val stationKeywordDao: StationKeywordDao,
    private val settingsDao: SettingsDao,
    private val entityConverters: EntityConverters
) : KoleoLocalRepository {

    override suspend fun getStations(): List<Station> {
        val stations = stationDao.getAllStations()
        return stations.map { entityConverters.stationEntityToStationConverter.convert(it) }
    }

    override suspend fun getStationKeywords(): List<StationKeyword> {
        val stationKeywords = stationKeywordDao.getAllStationKeywords()
        return stationKeywords.map { entityConverters.stationKeywordEntityToStationKeywordConverter.convert(it) }
    }

    override suspend fun insertCreationDate() {
        settingsDao.insert(SettingsEntity())
    }

    override suspend fun insertStations(stations: List<Station>) {
        stationDao.insertAll(stations.map { entityConverters.stationToEntityConverter.convert(it) })
    }

    override suspend fun insertStationKeywords(stationKeywords: List<StationKeyword>) {
        stationKeywordDao.insertAll(stationKeywords.map { entityConverters.stationKeywordToEntityConverter.convert(it) })
    }

    override suspend fun deleteAllStations() {
        stationDao.deleteAllStations()
    }

    override suspend fun deleteAllStationKeywords() {
        stationKeywordDao.deleteAllStationKeywords()
    }

    override suspend fun deleteAllSettings() {
        settingsDao.deleteAllSettings()
    }

    override suspend fun getSettings(): SettingsEntity {
        return settingsDao.getSettings() ?: SettingsEntity()
    }
}