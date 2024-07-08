package com.calcdistanceapp.domain.usecase

import com.calcdistanceapp.data.local.repository.KoleoLocalRepository
import com.calcdistanceapp.domain.model.Station
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SearchStationsByKeywordUseCaseTest {

    private lateinit var classUnderTest: SearchStationsByKeywordUseCase
    private lateinit var localRepository: KoleoLocalRepository

    @Before
    fun setUp() {
        localRepository = mock()
        classUnderTest = SearchStationsByKeywordUseCase(localRepository)
    }

    @Test
    fun `invoke should call searchStationsByKeyword in repository`(): Unit = runBlocking {
        val keyword = "test"
        val flow = flowOf<List<Station>>()
        whenever(localRepository.searchStationsByKeyword(keyword)).thenReturn(flow)
        classUnderTest.invoke(keyword)
        verify(localRepository).searchStationsByKeyword(keyword)
    }
}