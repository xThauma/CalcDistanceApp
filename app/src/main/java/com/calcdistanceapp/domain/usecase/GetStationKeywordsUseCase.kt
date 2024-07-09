package com.calcdistanceapp.domain.usecase

import com.calcdistanceapp.data.ConnectivityChecker
import com.calcdistanceapp.data.DataResult
import com.calcdistanceapp.data.isWithinLast24Hours
import com.calcdistanceapp.data.local.repository.KoleoLocalRepository
import com.calcdistanceapp.domain.model.StationKeyword
import com.calcdistanceapp.domain.repository.KoleoRemoteRepository
import javax.inject.Inject

class GetStationKeywordsUseCase @Inject constructor(
    private val koleoLocalRepository: KoleoLocalRepository,
    private val koleoRemoteRepository: KoleoRemoteRepository,
    private val connectivityChecker: ConnectivityChecker
) {
    suspend fun invoke(): DataResult<List<StationKeyword>> {
        return try {
            if (connectivityChecker.isNetworkAvailable()) {
                val settings = koleoLocalRepository.getSettings()
                if (settings.creationDate.isWithinLast24Hours()) {
                    val localKeywords = koleoLocalRepository.getStationKeywords()
                    if (localKeywords.isNotEmpty()) {
                        DataResult.Success.FetchLocal
                    } else {
                        fetchRemoteStationKeywords()
                    }
                } else {
                    fetchRemoteStationKeywords()
                }
            } else {
                val localKeywords = koleoLocalRepository.getStationKeywords()
                if (localKeywords.isNotEmpty()) {
                    DataResult.Success.FetchLocal
                } else {
                    val jsonStationKeywords = koleoLocalRepository.getStartingStationKeywords()
                    koleoLocalRepository.insertStationKeywords(jsonStationKeywords)
                    DataResult.Success.FetchJson
                }
            }
        } catch (e: Exception) {
            DataResult.Error.UnknownError(e)
        }
    }

    private suspend fun fetchRemoteStationKeywords(): DataResult<List<StationKeyword>> {
        koleoLocalRepository.deleteAllStationKeywords()
        val remoteKeywords = koleoRemoteRepository.getStationKeywords()
        koleoLocalRepository.insertStationKeywords(remoteKeywords)
        return DataResult.Success.FetchRemote
    }
}
