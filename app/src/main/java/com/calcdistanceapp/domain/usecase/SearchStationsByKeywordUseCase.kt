package com.calcdistanceapp.domain.usecase

import com.calcdistanceapp.data.local.repository.KoleoLocalRepository
import com.calcdistanceapp.domain.model.Station
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchStationsByKeywordUseCase @Inject constructor(
    private val koleoLocalRepository: KoleoLocalRepository
) {
    suspend operator fun invoke(keyword: String): Flow<List<Station>> {
        return koleoLocalRepository.searchStationsByKeyword(keyword)
    }
}