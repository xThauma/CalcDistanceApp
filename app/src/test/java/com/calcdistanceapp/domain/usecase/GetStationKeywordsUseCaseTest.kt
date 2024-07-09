package com.calcdistanceapp.domain.usecase

import com.calcdistanceapp.data.ConnectivityChecker
import com.calcdistanceapp.data.DataResult
import com.calcdistanceapp.data.local.model.SettingsEntity
import com.calcdistanceapp.data.local.repository.KoleoLocalRepository
import com.calcdistanceapp.domain.model.StationKeyword
import com.calcdistanceapp.domain.repository.KoleoRemoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate

class GetStationKeywordsUseCaseTest {

    private lateinit var classUnderTest: GetStationKeywordsUseCase
    private lateinit var localRepository: KoleoLocalRepository
    private lateinit var remoteRepository: KoleoRemoteRepository
    private lateinit var connectivityChecker: ConnectivityChecker

    @Before
    fun setUp() {
        localRepository = mock()
        remoteRepository = mock()
        connectivityChecker = mock()
        classUnderTest = GetStationKeywordsUseCase(localRepository, remoteRepository, connectivityChecker)
    }

    @Test
    fun `invoke should fetch remote keywords when network available and local data outdated`() = runBlocking {
        whenever(connectivityChecker.isNetworkAvailable()).thenReturn(true)
        val recentSettings = SettingsEntity(creationDate = LocalDate.now())
        whenever(localRepository.getSettings()).thenReturn(recentSettings)
        whenever(localRepository.getStationKeywords()).thenReturn(emptyList())
        val remoteKeywords = listOf(StationKeyword("keyword1", 1), StationKeyword("keyword2", 2))
        whenever(remoteRepository.getStationKeywords()).thenReturn(remoteKeywords)
        val result = classUnderTest.invoke()
        verify(localRepository).deleteAllStationKeywords()
        verify(localRepository).insertStationKeywords(remoteKeywords)
        assert(result is DataResult.Success.FetchRemote)
    }

    @Test
    fun `invoke should fetch local keywords when network available and local data recent`() = runBlocking {
        whenever(connectivityChecker.isNetworkAvailable()).thenReturn(true)
        val recentSettings = SettingsEntity(creationDate = LocalDate.now())
        whenever(localRepository.getSettings()).thenReturn(recentSettings)
        val localKeywords = listOf(StationKeyword("keyword1", 1), StationKeyword("keyword2", 2))
        whenever(localRepository.getStationKeywords()).thenReturn(localKeywords)
        val result = classUnderTest.invoke()
        assert(result is DataResult.Success.FetchLocal)
    }

    @Test
    fun `invoke should fetch json keywords when network unavailable and local data empty`() = runBlocking {
        whenever(connectivityChecker.isNetworkAvailable()).thenReturn(false)
        whenever(localRepository.getStationKeywords()).thenReturn(emptyList())
        val result = classUnderTest.invoke()
        assert(result is DataResult.Success.FetchJson)
    }

    @Test
    fun `invoke should fetch local keywords when network unavailable and local data present`() = runBlocking {
        whenever(connectivityChecker.isNetworkAvailable()).thenReturn(false)
        val localKeywords = listOf(StationKeyword("keyword1", 1), StationKeyword("keyword2", 2))
        whenever(localRepository.getStationKeywords()).thenReturn(localKeywords)
        val result = classUnderTest.invoke()
        assert(result is DataResult.Success.FetchLocal)
    }
}
