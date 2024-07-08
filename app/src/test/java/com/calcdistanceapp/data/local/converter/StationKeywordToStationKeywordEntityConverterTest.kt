package com.calcdistanceapp.data.local.converter

import com.calcdistanceapp.data.local.model.StationKeywordEntity
import com.calcdistanceapp.domain.model.StationKeyword
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class StationKeywordToStationKeywordEntityConverterTest {

    private val stringPolishAccentToStringNoAccentConverter: StringPolishAccentToStringNoAccentConverter = mock()
    private lateinit var classUnderTest: StationKeywordToStationKeywordEntityConverter

    @Before
    fun setUp() {
        classUnderTest = StationKeywordToStationKeywordEntityConverter(
            stringPolishAccentToStringNoAccentConverter
        )
    }

    @Test
    fun `convert should map all fields correctly`() {
        val stationKeyword = StationKeyword(
            keyword = "Gdańsk",
            stationId = 100
        )
        val expectedStationKeywordEntity = StationKeywordEntity(
            keyword = "gdansk",
            stationId = 100
        )

        whenever(stringPolishAccentToStringNoAccentConverter.convert(stationKeyword.keyword))
            .thenReturn("gdansk")

        val result = classUnderTest.convert(stationKeyword)

        assertEquals(expectedStationKeywordEntity, result)
    }
}
