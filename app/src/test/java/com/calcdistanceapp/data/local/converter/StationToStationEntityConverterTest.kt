package com.calcdistanceapp.data.local.converter

import com.calcdistanceapp.data.local.model.StationEntity
import com.calcdistanceapp.domain.model.Station
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class StationToStationEntityConverterTest {

    private lateinit var classUnderTest: StationToStationEntityConverter

    @Before
    fun setUp() {
        classUnderTest = StationToStationEntityConverter()
    }

    @Test
    fun `convert should map all fields correctly`() {
        val station = Station(
            id = 100,
            name = "Station Name",
            latitude = 52.2297,
            longitude = 21.0122,
            hits = 1000,
            city = "City",
            region = "Region",
            country = "Country",
            isGroup = false
        )
        val expectedStationEntity = StationEntity(
            stationId = 100,
            name = "Station Name",
            latitude = 52.2297,
            longitude = 21.0122,
            hits = 1000,
            city = "City",
            region = "Region",
            country = "Country",
            isGroup = false
        )

        val result = classUnderTest.convert(station)

        assertEquals(expectedStationEntity, result)
    }
}