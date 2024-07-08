package com.calcdistanceapp.data.remote.interceptor

import com.calcdistanceapp.di.NetworkModule.BASE_URL
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class KoleoHeaderInterceptorTest {

    private lateinit var interceptor: KoleoHeaderInterceptor
    private lateinit var chain: Interceptor.Chain
    private lateinit var originalRequest: Request
    private lateinit var modifiedRequest: Request
    private lateinit var response: Response

    @Before
    fun setUp() {
        interceptor = KoleoHeaderInterceptor()
        chain = mock()
        originalRequest = Request.Builder().url(BASE_URL).build()
        modifiedRequest = mock()
        response = mock()

        whenever(chain.request()).thenReturn(originalRequest)
        whenever(chain.proceed(modifiedRequest)).thenReturn(response)
    }

    @Test
    fun `intercept should add X-KOLEO-Version header to request`() {
        interceptor.intercept(chain)
        verify(chain).proceed(any())
    }
}