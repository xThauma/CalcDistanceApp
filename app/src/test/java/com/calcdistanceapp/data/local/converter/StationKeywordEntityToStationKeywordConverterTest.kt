package com.calcdistanceapp.data.local.converter

import com.calcdistanceapp.data.local.model.StationKeywordEntity
import com.calcdistanceapp.domain.model.StationKeyword
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class StationKeywordEntityToStationKeywordConverterTest {

    private lateinit var classUnderTest: StationKeywordEntityToStationKeywordConverter

    @Before
    fun setUp() {
        classUnderTest = StationKeywordEntityToStationKeywordConverter()
    }

    @Test
    fun `convert should map all fields correctly`() {
        val stationKeywordEntity = StationKeywordEntity(
            id = 1,
            keyword = "Test Keyword",
            stationId = 100
        )
        val expectedStationKeyword = StationKeyword(
            keyword = "Test Keyword",
            stationId = 100
        )

        val result = classUnderTest.convert(stationKeywordEntity)

        assertEquals(expectedStationKeyword, result)
    }
}