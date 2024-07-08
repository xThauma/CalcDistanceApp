package com.calcdistanceapp.data.local.converter

import com.calcdistanceapp.data.local.model.SettingsEntity
import com.calcdistanceapp.domain.model.Settings
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class SettingsEntityToSettingsConverterTest {

    private lateinit var classUnderTest: SettingsEntityToSettingsConverter

    @Before
    fun setUp() {
        classUnderTest = SettingsEntityToSettingsConverter()
    }

    @Test
    fun `convert should map creationDate correctly`() {
        val creationDate = LocalDate.now()
        val settingsEntity = SettingsEntity(creationDate = creationDate)
        val expectedSettings = Settings(creationDate = creationDate)

        val result = classUnderTest.convert(settingsEntity)

        assertEquals(expectedSettings, result)
    }
}
