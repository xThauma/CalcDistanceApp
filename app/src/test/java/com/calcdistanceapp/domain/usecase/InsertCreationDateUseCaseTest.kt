package com.calcdistanceapp.domain.usecase

import com.calcdistanceapp.data.local.repository.KoleoLocalRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class InsertCreationDateUseCaseTest {

    private lateinit var classUnderTest: InsertCreationDateUseCase
    private lateinit var localRepository: KoleoLocalRepository

    @Before
    fun setUp() {
        localRepository = mock()
        classUnderTest = InsertCreationDateUseCase(localRepository)
    }

    @Test
    fun `invoke should call insertCreationDate in repository`() = runBlocking {
        classUnderTest.invoke()
        verify(localRepository).insertCreationDate()
    }
}
