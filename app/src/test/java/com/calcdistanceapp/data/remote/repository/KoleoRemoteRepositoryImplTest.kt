package com.calcdistanceapp.data.remote.repository

import com.calcdistanceapp.data.remote.api.KoleoApiService
import com.calcdistanceapp.data.remote.converter.StationDtoToStationConverter
import com.calcdistanceapp.data.remote.converter.StationKeywordDtoToStationConverter
import com.calcdistanceapp.data.remote.model.StationDto
import com.calcdistanceapp.data.remote.model.StationKeywordDto
import com.calcdistanceapp.domain.model.Station
import com.calcdistanceapp.domain.model.StationKeyword
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class KoleoRemoteRepositoryImplTest {

    private val apiService: KoleoApiService = mock()
    private val stationDtoToStationConverter: StationDtoToStationConverter = mock()
    private val stationKeywordDtoToStationKeywordConverter: StationKeywordDtoToStationConverter = mock()

    private lateinit var classUnderTest: KoleoRemoteRepositoryImpl

    @Before
    fun setUp() {
        classUnderTest = KoleoRemoteRepositoryImpl(
            apiService = apiService,
            stationDtoToStationConverter = stationDtoToStationConverter,
            stationKeywordDtoToStationKeywordConverter = stationKeywordDtoToStationKeywordConverter
        )
    }

    @Test
    fun `getStations should return mapped list of stations`() = runBlocking {
        val stationDtoList = listOf(
            StationDto(1, "Station1", "station1", 52.22, 21.01, 100, 123456, "City1", "Region1", "Country1", "Station 1", false, true, true),
            StationDto(2, "Station2", "station2", 53.33, 22.02, 200, 654321, "City2", "Region2", "Country2", null, true, false, false)
        )

        val expectedStationList = listOf(
            Station(1, "Station1", 52.22, 21.01, 100, "City1", "Region1", "Country1", false),
            Station(2, "Station2", 53.33, 22.02, 200, "City2", "Region2", "Country2", true)
        )

        whenever(apiService.getStations()).thenReturn(stationDtoList)
        whenever(stationDtoToStationConverter.convert(stationDtoList[0])).thenReturn(expectedStationList[0])
        whenever(stationDtoToStationConverter.convert(stationDtoList[1])).thenReturn(expectedStationList[1])

        val result = classUnderTest.getStations()
        assertEquals(expectedStationList, result)
    }

    @Test
    fun `getStationKeywords should return mapped list of station keywords`() = runBlocking {
        val stationKeywordDtoList = listOf(
            StationKeywordDto(1, "Keyword1", 100),
            StationKeywordDto(2, "Keyword2", 200)
        )

        val expectedStationKeywordList = listOf(
            StationKeyword("Keyword1", 100),
            StationKeyword("Keyword2", 200)
        )

        whenever(apiService.getStationKeywords()).thenReturn(stationKeywordDtoList)
        whenever(stationKeywordDtoToStationKeywordConverter.convert(stationKeywordDtoList[0])).thenReturn(expectedStationKeywordList[0])
        whenever(stationKeywordDtoToStationKeywordConverter.convert(stationKeywordDtoList[1])).thenReturn(expectedStationKeywordList[1])

        val result = classUnderTest.getStationKeywords()
        assertEquals(expectedStationKeywordList, result)
    }
}