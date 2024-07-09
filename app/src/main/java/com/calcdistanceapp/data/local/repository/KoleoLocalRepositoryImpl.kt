package com.calcdistanceapp.data.local.repository

import android.content.Context
import com.calcdistanceapp.R
import com.calcdistanceapp.data.local.converter.EntityConverters
import com.calcdistanceapp.data.local.dao.SettingsDao
import com.calcdistanceapp.data.local.dao.StationDao
import com.calcdistanceapp.data.local.dao.StationKeywordDao
import com.calcdistanceapp.data.local.model.SettingsEntity
import com.calcdistanceapp.data.remote.model.StationDto
import com.calcdistanceapp.data.remote.model.StationKeywordDto
import com.calcdistanceapp.domain.model.Station
import com.calcdistanceapp.domain.model.StationKeyword
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import java.io.BufferedReader
import java.time.LocalDate
import javax.inject.Inject

class KoleoLocalRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val stationDao: StationDao,
    private val stationKeywordDao: StationKeywordDao,
    private val settingsDao: SettingsDao,
    private val entityConverters: EntityConverters,
    private val gson: Gson
) : KoleoLocalRepository {

    override suspend fun getStations(): List<Station> {
        val stations = stationDao.getAllStations()
        return stations.map { entityConverters.stationEntityToStationConverter.convert(it) }
    }

    override suspend fun getStationKeywords(): List<StationKeyword> {
        val stationKeywords = stationKeywordDao.getAllStationKeywords()
        return stationKeywords.map {
            entityConverters.stationKeywordEntityToStationKeywordConverter.convert(it)
        }
    }

    override suspend fun insertCreationDate() {
        settingsDao.insert(SettingsEntity(creationDate = LocalDate.now()))
    }

    override suspend fun insertStations(stations: List<Station>) {
        stationDao.insertAll(stations.map { entityConverters.stationToEntityConverter.convert(it) })
    }

    override suspend fun insertStationKeywords(stationKeywords: List<StationKeyword>) {
        stationKeywordDao.insertAll(stationKeywords.map {
            entityConverters.stationKeywordToStationKeywordEntityConverter.convert(it)
        })
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

    override suspend fun getStartingStations(): List<Station> {
        return loadJsonFromRaw<List<StationDto>>(R.raw.stations)
            .map { dto -> entityConverters.stationDtoToStationConverter.convert(dto) }
    }

    override suspend fun getStartingStationKeywords(): List<StationKeyword> {
        return loadJsonFromRaw<List<StationKeywordDto>>(R.raw.station_keywords)
            .map { dto -> entityConverters.stationKeywordDtoToStationConverter.convert(dto) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun searchStationsByKeyword(keyword: String): Flow<List<Station>> {
        val keywordNoAccents =
            entityConverters.stringPolishAccentToStringNoAccentConverter.convert(keyword)
        return stationKeywordDao.searchKeywords("%$keywordNoAccents%")
            .flatMapConcat { keywordEntities ->
                val stationIds = keywordEntities.map { it.stationId }
                stationDao.getStationsByIds(stationIds)
            }
            .map { stationEntities ->
                stationEntities.map { entityConverters.stationEntityToStationConverter.convert(it) }
            }
    }

    private inline fun <reified T> loadJsonFromRaw(rawResourceId: Int): T {
        val inputStream = context.resources.openRawResource(rawResourceId)
        val json = inputStream.bufferedReader().use(BufferedReader::readText)
        val listType = object : TypeToken<T>() {}.type
        return gson.fromJson(json, listType)
    }
}