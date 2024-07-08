package com.calcdistanceapp.domain.usecase

import com.calcdistanceapp.data.ConnectivityChecker
import com.calcdistanceapp.data.DataResult
import com.calcdistanceapp.data.local.model.SettingsEntity
import com.calcdistanceapp.data.local.repository.KoleoLocalRepository
import com.calcdistanceapp.domain.model.Station
import com.calcdistanceapp.domain.repository.KoleoRemoteRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate

class GetStationsUseCaseTest {

    private lateinit var classUnderTest: GetStationsUseCase
    private lateinit var localRepository: KoleoLocalRepository
    private lateinit var remoteRepository: KoleoRemoteRepository
    private lateinit var connectivityChecker: ConnectivityChecker

    @Before
    fun setUp() {
        localRepository = mock()
        remoteRepository = mock()
        connectivityChecker = mock()
        classUnderTest = GetStationsUseCase(localRepository, remoteRepository, connectivityChecker)
    }

    @Test
    fun `invoke should fetch remote stations when network available and local data outdated`() =
        runBlocking {
            whenever(connectivityChecker.isNetworkAvailable()).thenReturn(true)
            val recentSettings = SettingsEntity(creationDate = LocalDate.now())
            whenever(localRepository.getSettings()).thenReturn(recentSettings)
            whenever(localRepository.getStations()).thenReturn(emptyList())
            val remoteStations = listOf(
                Station(1, "Station 1", 52.23, 21.01, 0, "City 1", "Region 1", "Country 1", false),
                Station(2, "Station 2", 50.12, 19.30, 0, "City 2", "Region 2", "Country 2", false)
            )
            whenever(remoteRepository.getStations()).thenReturn(remoteStations)
            val result = classUnderTest.invoke()
            verify(localRepository).deleteAllStations()
            verify(localRepository).insertStations(remoteStations)
            assert(result is DataResult.Success.FetchRemote)
        }

    @Test
    fun `invoke should fetch local stations when network available and local data recent`() =
        runBlocking {
            whenever(connectivityChecker.isNetworkAvailable()).thenReturn(true)
            val recentSettings = SettingsEntity(creationDate = LocalDate.now())
            whenever(localRepository.getSettings()).thenReturn(recentSettings)
            val localStations = listOf(
                Station(1, "Station 1", 52.23, 21.01, 0, "City 1", "Region 1", "Country 1", false),
                Station(2, "Station 2", 50.12, 19.30, 0, "City 2", "Region 2", "Country 2", false)
            )
            whenever(localRepository.getStations()).thenReturn(localStations)
            val result = classUnderTest.invoke()
            assert(result is DataResult.Success.FetchLocal)
        }

    @Test
    fun `invoke should fetch remote stations when network unavailable and local data empty`() =
        runBlocking {
            whenever(connectivityChecker.isNetworkAvailable()).thenReturn(false)
            whenever(localRepository.getStations()).thenReturn(emptyList())
            val result = classUnderTest.invoke()
            assert(result is DataResult.Error.NoInternetError)
        }

    @Test
    fun `invoke should fetch local stations when network unavailable and local data present`() =
        runBlocking {
            whenever(connectivityChecker.isNetworkAvailable()).thenReturn(false)
            val localStations = listOf(
                Station(1, "Station 1", 52.23, 21.01, 0, "City 1", "Region 1", "Country 1", false),
                Station(2, "Station 2", 50.12, 19.30, 0, "City 2", "Region 2", "Country 2", false)
            )
            whenever(localRepository.getStations()).thenReturn(localStations)
            val result = classUnderTest.invoke()
            assert(result is DataResult.Success.FetchLocal)
        }
}