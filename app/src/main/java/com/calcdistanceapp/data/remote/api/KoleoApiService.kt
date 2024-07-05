package com.calcdistanceapp.data.remote.api

import com.calcdistanceapp.data.model.StationDto
import com.calcdistanceapp.data.model.StationKeywordDto
import retrofit2.http.GET
import retrofit2.http.Headers

interface KoleoApiService {
    @GET("main/stations")
    suspend fun getStations(): List<StationDto>

    @GET("main/station_keywords")
    suspend fun getStationKeywords(): List<StationKeywordDto>
}