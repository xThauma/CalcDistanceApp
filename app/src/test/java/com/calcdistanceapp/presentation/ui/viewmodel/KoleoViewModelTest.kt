package com.calcdistanceapp.presentation.ui.viewmodel

import com.calcdistanceapp.MainDispatcherRule
import com.calcdistanceapp.data.DataResult
import com.calcdistanceapp.domain.model.Station
import com.calcdistanceapp.domain.usecase.GetStationKeywordsUseCase
import com.calcdistanceapp.domain.usecase.GetStationsUseCase
import com.calcdistanceapp.domain.usecase.InsertCreationDateUseCase
import com.calcdistanceapp.domain.usecase.SearchStationsByKeywordUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class KoleoViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getStationsUseCase: GetStationsUseCase = mock()
    private val getStationKeywordsUseCase: GetStationKeywordsUseCase = mock()
    private val insertCreationDateUseCase: InsertCreationDateUseCase = mock()
    private val searchStationsByKeywordUseCase: SearchStationsByKeywordUseCase = mock()
    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var classUnderTest: KoleoViewModel

    @Test
    fun `fetchStationsAndKeywords should update loading state`() = runTest {
        whenever(getStationsUseCase.invoke()).thenReturn(DataResult.Success.FetchLocal)
        whenever(getStationKeywordsUseCase.invoke()).thenReturn(DataResult.Success.FetchLocal)
        whenever(searchStationsByKeywordUseCase.invoke(any())).thenReturn(mock())

        classUnderTest = KoleoViewModel(
            getStationsUseCase = getStationsUseCase,
            getStationKeywordsUseCase = getStationKeywordsUseCase,
            insertCreationDateUseCase = insertCreationDateUseCase,
            searchStationsByKeywordUseCase = searchStationsByKeywordUseCase,
            ioDispatcher = dispatcher
        )
        advanceUntilIdle()
        verify(insertCreationDateUseCase, never()).invoke()
        verify(searchStationsByKeywordUseCase).invoke("")
        assertEquals(false, classUnderTest.dataState.value.isLoading)
    }

    @Test
    fun `fetchStationsAndKeywords should invoke insertCreationDateUseCase on remote fetch`() = runTest {
        whenever(getStationsUseCase.invoke()).thenReturn(DataResult.Success.FetchRemote)
        whenever(getStationKeywordsUseCase.invoke()).thenReturn(DataResult.Success.FetchRemote)
        whenever(searchStationsByKeywordUseCase.invoke(any())).thenReturn(mock())

        classUnderTest = KoleoViewModel(
            getStationsUseCase = getStationsUseCase,
            getStationKeywordsUseCase = getStationKeywordsUseCase,
            insertCreationDateUseCase = insertCreationDateUseCase,
            searchStationsByKeywordUseCase = searchStationsByKeywordUseCase,
            ioDispatcher = dispatcher
        )
        advanceUntilIdle()
        verify(searchStationsByKeywordUseCase).invoke("")
        verify(insertCreationDateUseCase).invoke()
    }

    @Test
    fun `fetchStationsAndKeywords should handle error state`() = runTest {
        whenever(getStationsUseCase.invoke()).thenReturn(DataResult.Error.UnknownError(Exception("error")))
        whenever(getStationKeywordsUseCase.invoke()).thenReturn(DataResult.Success.FetchLocal)
        whenever(searchStationsByKeywordUseCase.invoke(any())).thenReturn(mock())

        classUnderTest = KoleoViewModel(
            getStationsUseCase = getStationsUseCase,
            getStationKeywordsUseCase = getStationKeywordsUseCase,
            insertCreationDateUseCase = insertCreationDateUseCase,
            searchStationsByKeywordUseCase = searchStationsByKeywordUseCase,
            ioDispatcher = dispatcher
        )
        advanceUntilIdle()
        verify(insertCreationDateUseCase, never()).invoke()
        assertEquals(false, classUnderTest.dataState.value.isLoading)
    }

    @Test
    fun `calculateDistanceBetweenStations should return correct distance`() {
        val station1 = Station(1, "Białystok", 53.12667092, 23.12500186, 0, "", "", "", false)
        val station2 = Station(2, "Warszawa centralna", 52.22944444, 21.00638888, 0, "", "", "", false)

        val expectedDistance = 174

        classUnderTest = KoleoViewModel(
            getStationsUseCase = getStationsUseCase,
            getStationKeywordsUseCase = getStationKeywordsUseCase,
            insertCreationDateUseCase = insertCreationDateUseCase,
            searchStationsByKeywordUseCase = searchStationsByKeywordUseCase,
            ioDispatcher = dispatcher
        )
        val distance = classUnderTest.calculateDistanceBetweenStations(station1, station2)

        assertEquals(expectedDistance, distance)
    }
}
