package com.calcdistanceapp.data.remote.converter

import com.calcdistanceapp.data.remote.model.StationDto
import com.calcdistanceapp.domain.model.Station
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class StationDtoToStationConverterTest {

    private lateinit var classUnderTest: StationDtoToStationConverter

    @Before
    fun setUp() {
        classUnderTest = StationDtoToStationConverter()
    }

    @Test
    fun `convert should map all fields correctly`() {
        val dto = StationDto(
            id = 1,
            name = "Test Station",
            nameSlug = "test-station",
            latitude = 52.0,
            longitude = 13.0,
            hits = 100,
            ibnr = 12345,
            city = "City",
            region = "Region",
            country = "Country",
            localizedName = "Test Station",
            isGroup = false,
            hasAnnouncements = true,
            isNearbyStationEnabled = false
        )

        val expectedStation = Station(
            id = 1,
            name = "Test Station",
            latitude = 52.0,
            longitude = 13.0,
            hits = 100,
            city = "City",
            region = "Region",
            country = "Country",
            isGroup = false
        )

        val result = classUnderTest.convert(dto)
        assertEquals(expectedStation, result)
    }
}
