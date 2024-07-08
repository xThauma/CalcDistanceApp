package com.calcdistanceapp.di

import com.calcdistanceapp.data.remote.api.KoleoApiService
import com.calcdistanceapp.data.remote.interceptor.KoleoErrorInterceptor
import com.calcdistanceapp.data.remote.interceptor.KoleoHeaderInterceptor
import com.calcdistanceapp.data.remote.interceptor.KoleoRetryInterceptor
import com.calcdistanceapp.domain.repository.KoleoRemoteRepository
import com.calcdistanceapp.mock.MockKoleoRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
abstract class TestNetworkModule {
    @Binds
    abstract fun bindKoleoRemoteRepository(repository: MockKoleoRemoteRepositoryImpl): KoleoRemoteRepository

    companion object {

        private const val TIMEOUT: Long = 5

        @Provides
        fun provideOkHttpClient(
            koleoHeaderInterceptor: KoleoHeaderInterceptor,
            koleoRetryInterceptor: KoleoRetryInterceptor,
            koleoErrorInterceptor: KoleoErrorInterceptor,
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
        fun provideRetrofit(
            client: OkHttpClient,
            mockWebServer: MockWebServer
        ): Retrofit {
            mockWebServer.start()
            return Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
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

        @Provides
        fun provideMockWebServer(): MockWebServer {
            return MockWebServer()
        }
    }
}