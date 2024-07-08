package com.calcdistanceapp.data.local.converter

import junit.framework.TestCase.assertEquals
import org.junit.Test

class StringPolishAccentToStringNoAccentConverterTest {

    private val classUnderTest = StringPolishAccentToStringNoAccentConverter()

    @Test
    fun `convert should remove diacritics and lowercase the string`() {
        val input = "Gdańsk"
        val expectedOutput = "gdansk"
        val result = classUnderTest.convert(input)
        assertEquals(expectedOutput, result)
    }

    @Test
    fun `convert should handle multiple Polish characters`() {
        val input = "Łódź"
        val expectedOutput = "lodz"
        val result = classUnderTest.convert(input)
        assertEquals(expectedOutput, result)
    }

    @Test
    fun `convert should leave non-Polish characters unchanged`() {
        val input = "Berlin"
        val expectedOutput = "berlin"
        val result = classUnderTest.convert(input)
        assertEquals(expectedOutput, result)
    }
}
