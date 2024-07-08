package com.calcdistanceapp.data.remote.interceptor

import com.calcdistanceapp.data.remote.error.KoleoApiErrorException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class KoleoErrorInterceptorTest {

    private lateinit var interceptor: KoleoErrorInterceptor
    private lateinit var chain: Interceptor.Chain
    private lateinit var request: Request
    private lateinit var response: Response

    @Before
    fun setUp() {
        interceptor = KoleoErrorInterceptor()
        chain = mock()
        request = mock()
        response = mock()

        whenever(chain.request()).thenReturn(request)
        whenever(chain.proceed(request)).thenReturn(response)
    }

    @Test(expected = KoleoApiErrorException::class)
    fun `intercept should throw KoleoApiErrorException when response is not successful`() {
        whenever(response.isSuccessful).thenReturn(false)
        interceptor.intercept(chain)
    }

    @Test
    fun `intercept should return response when response is successful`() {
        whenever(response.isSuccessful).thenReturn(true)
        val result = interceptor.intercept(chain)
        assertEquals(response, result)
    }
}
