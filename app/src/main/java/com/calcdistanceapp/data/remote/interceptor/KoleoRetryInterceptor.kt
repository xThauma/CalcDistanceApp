package com.calcdistanceapp.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class KoleoRetryInterceptor @Inject constructor(private val maxRetries: Int = DEFAULT_MAX_RETRIES) :
    Interceptor {

    companion object {
        private const val DEFAULT_MAX_RETRIES = 3
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var response = chain.proceed(request)
        var retryCount = 0

        while (!response.isSuccessful && retryCount < maxRetries) {
            retryCount++
            response.close()
            request = request.newBuilder().build()
            response = chain.proceed(request)
        }

        return response
    }
}