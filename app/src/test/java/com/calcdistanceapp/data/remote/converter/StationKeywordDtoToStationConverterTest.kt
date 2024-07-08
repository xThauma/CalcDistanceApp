package com.calcdistanceapp.data.remote.converter

import com.calcdistanceapp.data.remote.model.StationKeywordDto
import com.calcdistanceapp.domain.model.StationKeyword
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class StationKeywordDtoToStationKeywordConverterTest {

    private lateinit var classUnderTest: StationKeywordDtoToStationConverter

    @Before
    fun setUp() {
        classUnderTest = StationKeywordDtoToStationConverter()
    }

    @Test
    fun `convert should map all fields correctly`() {
        val dto = StationKeywordDto(
            id = 1,
            keyword = "Gdańsk",
            stationId = 100
        )

        val expectedStationKeyword = StationKeyword(
            keyword = "Gdańsk",
            stationId = 100
        )

        val result = classUnderTest.convert(dto)

        assertEquals(expectedStationKeyword, result)
    }
}
