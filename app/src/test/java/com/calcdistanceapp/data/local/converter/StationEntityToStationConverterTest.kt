package com.calcdistanceapp.data.local.converter

import com.calcdistanceapp.data.local.model.StationEntity
import com.calcdistanceapp.domain.model.Station
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class StationEntityToStationConverterTest {

    private lateinit var classUnderTest: StationEntityToStationConverter

    @Before
    fun setUp() {
        classUnderTest = StationEntityToStationConverter()
    }

    @Test
    fun `convert should map all fields correctly`() {
        val stationEntity = StationEntity(
            id = 1,
            stationId = 100,
            name = "Test Station",
            latitude = 50.0,
            longitude = 19.0,
            hits = 100,
            city = "Test City",
            region = "Test Region",
            country = "Test Country",
            isGroup = false
        )
        val expectedStation = Station(
            id = 100,
            name = "Test Station",
            latitude = 50.0,
            longitude = 19.0,
            hits = 100,
            city = "Test City",
            region = "Test Region",
            country = "Test Country",
            isGroup = false
        )

        val result = classUnderTest.convert(stationEntity)

        assertEquals(expectedStation, result)
    }
}