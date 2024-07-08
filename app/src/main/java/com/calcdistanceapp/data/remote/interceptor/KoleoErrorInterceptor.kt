package com.calcdistanceapp.data.remote.interceptor

import com.calcdistanceapp.data.remote.error.KoleoApiErrorException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class KoleoErrorInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (!response.isSuccessful) {
            throw KoleoApiErrorException()
        }
        return response
    }
}