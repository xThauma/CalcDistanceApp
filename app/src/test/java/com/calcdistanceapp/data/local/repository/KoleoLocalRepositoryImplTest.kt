package com.calcdistanceapp.data.local.repository

import android.content.Context
import com.calcdistanceapp.data.local.converter.EntityConverters
import com.calcdistanceapp.data.local.dao.SettingsDao
import com.calcdistanceapp.data.local.dao.StationDao
import com.calcdistanceapp.data.local.dao.StationKeywordDao
import com.calcdistanceapp.data.local.model.SettingsEntity
import com.calcdistanceapp.data.local.model.StationEntity
import com.calcdistanceapp.data.local.model.StationKeywordEntity
import com.calcdistanceapp.domain.model.Station
import com.calcdistanceapp.domain.model.StationKeyword
import com.google.gson.Gson
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class KoleoLocalRepositoryImplTest {

    private val stationDao: StationDao = mock()
    private val stationKeywordDao: StationKeywordDao = mock()
    private val settingsDao: SettingsDao = mock()
    private val gson: Gson = mock()
    private val context: Context = mock()
    private val entityConverters: EntityConverters = mock {
        on { stationEntityToStationConverter }.thenReturn(mock())
        on { stationKeywordEntityToStationKeywordConverter }.thenReturn(mock())
        on { stationToEntityConverter }.thenReturn(mock())
        on { stationKeywordToStationKeywordEntityConverter }.thenReturn(mock())
        on { stringPolishAccentToStringNoAccentConverter }.thenReturn(mock())
        on { stationDtoToStationConverter }.thenReturn(mock())
        on { stationKeywordDtoToStationConverter }.thenReturn(mock())
    }
    private lateinit var koleoLocalRepositoryImpl: KoleoLocalRepositoryImpl

    @Before
    fun setUp() {
        koleoLocalRepositoryImpl = KoleoLocalRepositoryImpl(
            context = context,
            stationDao = stationDao,
            stationKeywordDao = stationKeywordDao,
            settingsDao = settingsDao,
            entityConverters = entityConverters,
            gson = gson
        )
    }

    @Test
    fun `getStations should return list of Station`() = runBlocking {
        val stationEntities = listOf(
            StationEntity(1, 1, "Station 1", 52.23, 21.01, 0, "City1", "Region1", "Country1", false)
        )
        val expectedStations = listOf(
            Station(1, "Station 1", 52.23, 21.01, 0, "City1", "Region1", "Country1", false)
        )
        whenever(stationDao.getAllStations()).thenReturn(stationEntities)
        whenever(entityConverters.stationEntityToStationConverter.convert(any())).thenAnswer {
            val entity = it.arguments[0] as StationEntity
            Station(
                id = entity.stationId,
                name = entity.name,
                latitude = entity.latitude,
                longitude = entity.longitude,
                hits = entity.hits,
                city = entity.city,
                region = entity.region,
                country = entity.country,
                isGroup = entity.isGroup
            )
        }

        val result = koleoLocalRepositoryImpl.getStations()

        assertEquals(expectedStations, result)
    }

    @Test
    fun `getStationKeywords should return list of StationKeyword`() = runBlocking {
        val stationKeywordEntities = listOf(
            StationKeywordEntity(1, "keyword1", 1),
            StationKeywordEntity(2, "keyword2", 1)
        )
        val expectedStationKeywords = listOf(
            StationKeyword("keyword1", 1),
            StationKeyword("keyword2", 1)
        )
        whenever(stationKeywordDao.getAllStationKeywords()).thenReturn(stationKeywordEntities)
        whenever(entityConverters.stationKeywordEntityToStationKeywordConverter.convert(any())).thenAnswer {
            val entity = it.arguments[0] as StationKeywordEntity
            StationKeyword(entity.keyword, entity.stationId)
        }

        val result = koleoLocalRepositoryImpl.getStationKeywords()

        assertEquals(expectedStationKeywords, result)
    }

    @Test
    fun `insertCreationDate should insert SettingsEntity`() = runBlocking {
        koleoLocalRepositoryImpl.insertCreationDate()
        verify(settingsDao).insert(any())
    }

    @Test
    fun `insertStations should insert list of Station`() = runBlocking {
        val stations = listOf(
            Station(1, "Station 1", 52.23, 21.01, 0, "City1", "Region1", "Country1", false)
        )
        val stationEntities = listOf(
            StationEntity(1, 1, "Station 1", 52.23, 21.01, 0, "City1", "Region1", "Country1", false)
        )
        whenever(entityConverters.stationToEntityConverter.convert(any())).thenReturn(stationEntities.first())

        koleoLocalRepositoryImpl.insertStations(stations)

        verify(stationDao).insertAll(stationEntities)
    }

    @Test
    fun `insertStationKeywords should insert list of StationKeyword`() = runBlocking {
        val stationKeywords = listOf(
            StationKeyword("keyword1", 1),
        )
        val stationKeywordEntities = listOf(
            StationKeywordEntity(0, "keyword1", 1),
        )
        whenever(entityConverters.stationKeywordToStationKeywordEntityConverter.convert(any())).thenReturn(stationKeywordEntities.first())

        koleoLocalRepositoryImpl.insertStationKeywords(stationKeywords)

        verify(stationKeywordDao).insertAll(stationKeywordEntities)
    }

    @Test
    fun `deleteAllStations should call deleteAllStations`() = runBlocking {
        koleoLocalRepositoryImpl.deleteAllStations()
        verify(stationDao).deleteAllStations()
    }

    @Test
    fun `deleteAllStationKeywords should call deleteAllStationKeywords`() = runBlocking {
        koleoLocalRepositoryImpl.deleteAllStationKeywords()
        verify(stationKeywordDao).deleteAllStationKeywords()
    }

    @Test
    fun `deleteAllSettings should call deleteAllSettings`() = runBlocking {
        koleoLocalRepositoryImpl.deleteAllSettings()
        verify(settingsDao).deleteAllSettings()
    }

    @Test
    fun `getSettings should return SettingsEntity`() = runBlocking {
        val settingsEntity = SettingsEntity()
        whenever(settingsDao.getSettings()).thenReturn(settingsEntity)

        val result = koleoLocalRepositoryImpl.getSettings()

        assertEquals(settingsEntity, result)
    }
}