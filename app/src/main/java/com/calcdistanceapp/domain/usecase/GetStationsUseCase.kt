package com.calcdistanceapp.domain.usecase

import com.calcdistanceapp.data.ConnectivityChecker
import com.calcdistanceapp.data.DataResult
import com.calcdistanceapp.data.isWithinLast24Hours
import com.calcdistanceapp.data.local.repository.KoleoLocalRepository
import com.calcdistanceapp.domain.model.Station
import com.calcdistanceapp.domain.repository.KoleoRemoteRepository
import javax.inject.Inject

class GetStationsUseCase @Inject constructor(
    private val koleoLocalRepository: KoleoLocalRepository,
    private val koleoRemoteRepository: KoleoRemoteRepository,
    private val connectivityChecker: ConnectivityChecker
) {
    suspend fun invoke(): DataResult<List<Station>> {
        return try {
            if (connectivityChecker.isNetworkAvailable()) {
                val settings = koleoLocalRepository.getSettings()
                if (settings.creationDate.isWithinLast24Hours()) {
                    val localStations = koleoLocalRepository.getStations()
                    if (localStations.isNotEmpty()) {
                        DataResult.Success.FetchLocal
                    } else {
                        fetchRemoteStations()
                    }
                } else {
                    fetchRemoteStations()
                }
            } else {
                val localStations = koleoLocalRepository.getStations()
                if (localStations.isNotEmpty()) {
                    DataResult.Success.FetchLocal
                } else {
                    DataResult.Error.NoInternetError
                }
            }
        } catch (e: Exception) {
            DataResult.Error.UnknownError(e)
        }
    }

    private suspend fun fetchRemoteStations(): DataResult<List<Station>> {
        koleoLocalRepository.deleteAllStations()
        val remoteStations = koleoRemoteRepository.getStations()
        koleoLocalRepository.insertStations(remoteStations)
        return DataResult.Success.FetchRemote
    }
}
