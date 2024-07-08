package com.calcdistanceapp.di

import androidx.annotation.VisibleForTesting
import com.calcdistanceapp.data.remote.api.KoleoApiService
import com.calcdistanceapp.data.remote.interceptor.KoleoErrorInterceptor
import com.calcdistanceapp.data.remote.interceptor.KoleoHeaderInterceptor
import com.calcdistanceapp.data.remote.interceptor.KoleoRetryInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @VisibleForTesting
    const val BASE_URL = "https://koleo.pl/api/v2/"
    private const val TIMEOUT: Long = 5

    @Provides
    fun provideOkHttpClient(
        koleoHeaderInterceptor: KoleoHeaderInterceptor,
        koleoRetryInterceptor: KoleoRetryInterceptor,
        koleoErrorInterceptor: KoleoErrorInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(koleoHeaderInterceptor)
            .addInterceptor(koleoRetryInterceptor)
            .addInterceptor(koleoErrorInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideKoleoApiService(retrofit: Retrofit): KoleoApiService {
        return retrofit.create(KoleoApiService::class.java)
    }

    @Provides
    fun provideKoleoHeaderInterceptor(): KoleoHeaderInterceptor {
        return KoleoHeaderInterceptor()
    }

    @Provides
    fun provideKoleoRetryInterceptor(): KoleoRetryInterceptor {
        return KoleoRetryInterceptor()
    }

    @Provides
    fun provideKoleoErrorInterceptor(): KoleoErrorInterceptor {
        return KoleoErrorInterceptor()
    }
}