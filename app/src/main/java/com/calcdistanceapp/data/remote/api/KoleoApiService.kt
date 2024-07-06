package com.calcdistanceapp.data.remote.api

import com.calcdistanceapp.data.remote.model.StationDto
import com.calcdistanceapp.data.remote.model.StationKeywordDto
import retrofit2.http.GET

interface KoleoApiService {
    @GET("main/stations")
    suspend fun getStations(): List<StationDto>

    @GET("main/station_keywords")
    suspend fun getStationKeywords(): List<StationKeywordDto>
}