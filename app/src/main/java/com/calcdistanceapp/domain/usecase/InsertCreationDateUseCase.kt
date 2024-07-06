package com.calcdistanceapp.domain.usecase

import com.calcdistanceapp.data.local.repository.KoleoLocalRepository
import javax.inject.Inject

class InsertCreationDateUseCase @Inject constructor(
    private val koleoLocalRepository: KoleoLocalRepository
) {
    suspend fun invoke() {
        koleoLocalRepository.insertCreationDate()
    }

}